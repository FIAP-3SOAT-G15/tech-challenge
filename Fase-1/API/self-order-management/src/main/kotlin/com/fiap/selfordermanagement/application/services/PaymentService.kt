package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.Payment
import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.application.ports.incoming.LoadPaymentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.ProvidePaymentRequestUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SyncPaymentStatusUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentProvider
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentRepository
import java.math.BigDecimal
import java.time.LocalDateTime

class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val paymentProvider: PaymentProvider,
) :
    LoadPaymentUseCase,
        ProvidePaymentRequestUseCase,
        SyncPaymentStatusUseCase {
    override fun getByOrderNumber(orderNumber: Long): Payment {
        return paymentRepository.findByOrderNumber(orderNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PAYMENT_NOT_FOUND,
                message = "Payment not found for order [$orderNumber]",
            )
    }

    override fun findByOrderNumber(orderNumber: Long): Payment? {
        return paymentRepository.findByOrderNumber(orderNumber)
    }

    override fun findAll(): List<Payment> {
        return paymentRepository.findAll()
    }

    override fun provideNew(
        orderNumber: Long,
        amount: BigDecimal,
    ): PaymentRequest {
        val paymentRequest = paymentProvider.createPaymentRequest(orderNumber.toString(), amount)
        val payment =
            Payment(
                orderNumber = orderNumber,
                externalId = paymentRequest.externalId,
                createdAt = LocalDateTime.now(),
                status = PaymentStatus.PENDING,
                statusChangedAt = LocalDateTime.now(),
            )

        paymentRepository.create(payment)

        return paymentRequest
    }

    override fun provideWith(
        order: Order,
        payment: Payment,
    ): PaymentRequest {
        val syncedPayment = syncPaymentStatus(payment)

        return when (syncedPayment.status) {
            PaymentStatus.PENDING -> paymentProvider.getPaymentRequest(syncedPayment.externalId)
            PaymentStatus.EXPIRED, PaymentStatus.FAILED -> {
                // old payment is already synced and persisted
                // thus, create a new payment request
                val paymentRequest = paymentProvider.createPaymentRequest(payment.orderNumber.toString(), order.total)
                val newPayment =
                    payment.copy(
                        externalId = paymentRequest.externalId,
                        status = PaymentStatus.PENDING,
                        statusChangedAt = LocalDateTime.now(),
                    )
                paymentRepository.create(newPayment)
                paymentRequest
            }
            PaymentStatus.CONFIRMED -> throw SelfOrderManagementException(
                errorType = ErrorType.PAYMENT_REQUEST_NOT_ALLOWED,
                message = "Will not create a new payment request for an order with a confirmed payment",
            )
        }
    }

    override fun syncPaymentStatus(payment: Payment): Payment {
        val updatedPayment =
            paymentProvider.verifyPaymentStatus(payment.externalId).let { newStatus ->
                if (payment.status != newStatus) {
                    payment.copy(
                        createdAt = LocalDateTime.now(),
                        status = newStatus,
                        statusChangedAt = LocalDateTime.now(),
                    ).also { paymentRepository.update(it) }
                } else {
                    payment
                }
            }
        return updatedPayment
    }
}
