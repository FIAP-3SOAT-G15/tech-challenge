package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.usecases.services.CustomerService
import createCustomer
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class CustomerServiceTest {
    private val customerRepository = mockk<CustomerGateway>()

    private val customerService =
        CustomerService(
            customerRepository,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByDocumentTest {
        @Test
        fun `getByDocument should return a Customer when it exists`() {
            val customer = createCustomer()

            every { customerRepository.findById(customer.id) } returns customer

            val result = customerService.getById(customer.id)

            assertThat(result).isEqualTo(customer)
        }

        @Test
        fun `getById should throw an exception when the customer is not found`() {
            val document = UUID.randomUUID()

            every { customerRepository.findById(document) } returns null

            assertThatThrownBy { customerService.getById(document) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.CUSTOMER_NOT_FOUND)
        }
    }
}
