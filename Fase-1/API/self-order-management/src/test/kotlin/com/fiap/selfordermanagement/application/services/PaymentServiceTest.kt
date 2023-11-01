package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentProvider
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentRepository
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.math.BigDecimal

class PaymentServiceTest {
    private val paymentRepository = mockk<PaymentRepository>()
    private val paymentProvider = mockk<PaymentProvider>()

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
    inner class ProvideNewTest {
        @Test
        fun `provideNew should create a new PaymentRequest and a corresponding Payment`() {
            val orderNumber = 98765L
            val order = createOrder()
            val amount = BigDecimal("100.00")

            val paymentRequest = createPaymentRequest()

            every { paymentProvider.createPaymentRequest(orderNumber.toString(), amount) } returns paymentRequest
            every { paymentRepository.create(any()) } answers { firstArg() }

            val result = paymentService.provideNew(orderNumber, amount)

            assertThat(result).isNotNull()
            assertThat(result.externalId).isEqualTo(paymentRequest.externalId)

            verify { paymentRepository.create(any()) }
        }
    }

    @Nested
    inner class ProvideWithTest {
        @Test
        fun `provideWith should get PaymentRequest for a PENDING payment and return it`() {
            val orderNumber = 98765L
            val order = createOrder(number = orderNumber)
            val payment = createPayment(orderNumber = orderNumber, status = PaymentStatus.PENDING)
            val paymentRequest = createPaymentRequest()

            every { paymentService.syncPaymentStatus(payment) } returns payment
            every { paymentProvider.verifyPaymentStatus(payment.externalId) } returns PaymentStatus.PENDING
            every { paymentRepository.update(any()) } answers { firstArg() }
            every { paymentProvider.getPaymentRequest(payment.externalId) } returns paymentRequest

            val result = paymentService.provideWith(order, payment)

            assertThat(result).isNotNull()
            assertThat(result.externalId).isEqualTo(payment.externalId)
        }

        @ParameterizedTest
        @EnumSource(PaymentStatus::class, names = ["EXPIRED", "FAILED"])
        fun `provideWith should create a new PaymentRequest and Payment in the valid state and return the new PaymentRequest`(
            paymentStatus: PaymentStatus,
        ) {
            val orderNumber = 98765L
            val order = createOrder(number = orderNumber)
            val payment = createPayment(orderNumber = orderNumber, status = paymentStatus)
            val paymentRequest = createPaymentRequest()

            every { paymentService.syncPaymentStatus(payment) } returns payment
            every { paymentProvider.verifyPaymentStatus(payment.externalId) } returns paymentStatus
            every { paymentRepository.update(any()) } answers { firstArg() }
            every { paymentProvider.createPaymentRequest(any(), any()) } returns paymentRequest
            every { paymentRepository.create(any()) } answers { firstArg() }

            val result = paymentService.provideWith(order, payment)

            assertThat(result).isNotNull()
            assertThat(result.externalId).isEqualTo(payment.externalId)

            verify { paymentRepository.create(any()) }
        }

        @Test
        fun `provideWith should throw an exception for a CONFIRMED payment`() {
            val order = createOrder()
            val payment = createPayment(status = PaymentStatus.PENDING)

            every { paymentProvider.verifyPaymentStatus(payment.externalId) } returns PaymentStatus.CONFIRMED
            every { paymentRepository.update(any()) } answers { firstArg() }

            assertThatThrownBy { paymentService.provideWith(order, payment) }
                .isInstanceOf(SelfOrderManagementException::class.java)
                .hasFieldOrPropertyWithValue("errorType", ErrorType.PAYMENT_REQUEST_NOT_ALLOWED)
        }
    }

    @Nested
    inner class SyncPaymentStatusTest {
        @Test
        fun `syncPaymentStatus should update the payment status when it's different from the new status`() {
            val payment = createPayment(status = PaymentStatus.PENDING)
            val newStatus = PaymentStatus.CONFIRMED

            every { paymentProvider.verifyPaymentStatus(any()) } returns newStatus
            every { paymentRepository.update(any()) } answers { firstArg() }

            val result = paymentService.syncPaymentStatus(payment)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(newStatus)

            verify { paymentRepository.update(any()) }
        }

        @Test
        fun `syncPaymentStatus should not update the payment status when it's the same as the new status`() {
            val payment = createPayment(status = PaymentStatus.PENDING)
            val newStatus = PaymentStatus.PENDING

            every { paymentProvider.verifyPaymentStatus(any()) } returns newStatus

            val result = paymentService.syncPaymentStatus(payment)

            assertThat(result).isNotNull()
            assertThat(result.status).isEqualTo(newStatus)
        }
    }
}
