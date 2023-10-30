package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import createCustomer
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class CustomerServiceTest {
    private val customerRepository = mockk<CustomerRepository>()

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

            every { customerRepository.findByDocument(customer.document) } returns customer

            val result = customerService.getByDocument(customer.document)

            assertThat(result).isEqualTo(customer)
        }

        @Test
        fun `getByDocument should throw an exception when the customer is not found`() {
            val document = "444.555.666-77"

            every { customerRepository.findByDocument(document) } returns null

            assertThatThrownBy { customerService.getByDocument(document) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.CUSTOMER_NOT_FOUND)
        }
    }
}
