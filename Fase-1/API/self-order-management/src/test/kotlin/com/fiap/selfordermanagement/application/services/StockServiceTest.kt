package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.InputRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class StockServiceTest {
    private val inputRepository = mockk<InputRepository>()
    private val productRepository = mockk<ProductRepository>()

    private val stockService =
        StockService(
            productRepository,
            inputRepository,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByProductNumberTest {
        @Test
        fun `getByProductNumber should return a Stock when it exists`() {
            val input = createInput()

            every { productRepository.findByProductNumber(input.number!!) } returns createProduct(inputs = listOf(input))

            val result = stockService.getByProductNumber(input.number!!)

            assertThat(result.first()).isEqualTo(input)
        }

        @Test
        fun `getByProductNumber should throw an exception when the stock is not found`() {
            val productNumber = 123L

            every { productRepository.findByProductNumber(productNumber) } returns null

            assertThatThrownBy { stockService.getByProductNumber(productNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PRODUCT_NOT_FOUND)
        }
    }

    @Nested
    inner class IncrementTest {
        @Test
        fun `increment should increase the stock quantity for a given product number`() {
            val initialQuantity = 100L
            val incrementQuantity = 100L
            val stock = createInput(stock = createStock(quantity = initialQuantity))

            every { inputRepository.findByInputNumber(stock.number!!) } returns stock
            every { inputRepository.update(any()) } answers { firstArg() }

            val result = stockService.increment(stock.number!!, incrementQuantity)

            assertThat(result).isNotNull
            assertThat(result.number).isEqualTo(stock.number)
            assertThat(result.stock.quantity).isEqualTo(initialQuantity + incrementQuantity)
        }
    }

    @Nested
    inner class DecrementTest {
        @Test
        fun `decrement should reduce the stock quantity for a given product number`() {
            val initialQuantity = 100L
            val decrementQuantity = 50L
            val stock = createInput(stock = createStock(quantity = initialQuantity))

            every { inputRepository.findByInputNumber(stock.number!!) } returns stock
            every { inputRepository.update(any()) } answers { firstArg() }

            val result = stockService.decrement(stock.number!!, decrementQuantity)

            assertThat(result).isNotNull
            assertThat(result.number).isEqualTo(stock.number)
            assertThat(result.stock.quantity).isEqualTo(initialQuantity - decrementQuantity)
        }

        @Test
        fun `decrement should throw an exception for insufficient stock`() {
            val initialQuantity = 100L
            val decrementQuantity = 100L
            val stock = createInput(stock = createStock(quantity = initialQuantity))

            every { inputRepository.findByInputNumber(stock.number!!) } returns stock

            assertThatThrownBy { stockService.decrement(stock.number!!, decrementQuantity) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.INSUFFICIENT_STOCK)
        }
    }
}
