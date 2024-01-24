package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapter.gateway.ComponentGateway
import com.fiap.selfordermanagement.adapter.gateway.ProductGateway
import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.usecases.services.ComponentService
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import createComponent
import createProduct
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ComponentServiceTest {
    private val componentRepository = mockk<ComponentGateway>()
    private val stockRepository = mockk<StockGateway>()
    private val productRepository = mockk<ProductGateway>()

    private val componentService =
        ComponentService(
            componentRepository,
            stockRepository,
            productRepository,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByComponentNumberTest {
        @Test
        fun `getByComponentNumber should return a Component when it exists`() {
            val component = createComponent()

            every { componentRepository.findByComponentNumber(component.number!!) } returns component

            val result = componentService.getByComponentNumber(component.number!!)

            assertThat(result).isEqualTo(component)
        }

        @Test
        fun `getByComponentNumber should throw an exception when the component is not found`() {
            val componentNumber = 123L

            every { componentRepository.findByComponentNumber(componentNumber) } returns null

            assertThatThrownBy { componentService.getByComponentNumber(componentNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.COMPONENT_NOT_FOUND)
        }
    }

    @Nested
    inner class FindByProductNumberTest {
        @Test
        fun `findByProductNumber should return a list of components when it exists`() {
            val components = listOf(createComponent())
            val product = createProduct(components = components)

            every { productRepository.findByProductNumber(product.number!!) } returns product

            val result = componentService.findByProductNumber(product.number!!)

            assertThat(result).isEqualTo(components)
        }

        @Test
        fun `findByProductNumber should throw an exception when the product is not found`() {
            val productNumber = 123L

            every { productRepository.findByProductNumber(productNumber) } returns null

            assertThatThrownBy { componentService.findByProductNumber(productNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PRODUCT_NOT_FOUND)
        }
    }
}
