package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.adapter.gateway.PaymentGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import createOrder
import createPayment
import createPaymentRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import services.PaymentService
import java.math.BigDecimal

class PaymentServiceTest {
    private val paymentRepository = mockk<PaymentGateway>()
    private val paymentProvider = mockk<PaymentProviderGateway>()

    private val paymentService =
        PaymentService(
            paymentRepository,
            paymentProvider,
        )

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Nested
    inner class GetByOrderNumberTest {
        @Test
        fun `getByOrderNumberTest should return a Payment when it exists`() {
            val payment = createPayment()
            val order = createOrder()

            every { paymentRepository.findByOrderNumber(payment.orderNumber) } returns payment

            val result = paymentService.getByOrderNumber(payment.orderNumber)

            assertThat(result).isEqualTo(payment)
        }

        @Test
        fun `getByOrderNumberTest should throw an exception when the payment is not found`() {
            val orderNumber = 98765L

            every { paymentRepository.findByOrderNumber(orderNumber) } returns null

            assertThatThrownBy { paymentService.getByOrderNumber(orderNumber) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PAYMENT_NOT_FOUND)
        }
    }

    @Nested
    inner class ProvidePaymentRequestTest {
        @Test
        fun `providePaymentRequest should create a new PaymentRequest and a corresponding Payment`() {
            val order = createOrder()
            val amount = BigDecimal("100.00")

            val paymentRequest = createPaymentRequest()

            every { paymentProvider.createExternalOrder(order) } returns paymentRequest
            every { paymentRepository.create(any()) } answers { firstArg() }

            val result = paymentService.providePaymentRequest(order)

            assertThat(result).isNotNull()
            assertThat(result.externalOrderId).isEqualTo(paymentRequest.externalOrderId)
            assertThat(result.paymentInfo).isEqualTo(paymentRequest.paymentInfo)

            verify { paymentRepository.create(any()) }
        }
    }
}
