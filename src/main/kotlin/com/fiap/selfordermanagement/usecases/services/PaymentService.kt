package services

import com.fiap.selfordermanagement.adapter.gateway.PaymentGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.Payment
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import com.fiap.selfordermanagement.usecases.ProvidePaymentRequestUseCase
import java.time.LocalDateTime

class PaymentService(
    private val paymentRepository: PaymentGateway,
    private val paymentProvider: PaymentProviderGateway,
) :
    LoadPaymentUseCase,
        ProvidePaymentRequestUseCase {
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

    override fun providePaymentRequest(order: Order): PaymentRequest {
        val paymentRequest = paymentProvider.createExternalOrder(order)
        val payment =
            Payment(
                orderNumber = order.number!!,
                externalOrderId = paymentRequest.externalOrderId,
                externalOrderGlobalId = null,
                paymentInfo = paymentRequest.paymentInfo,
                createdAt = LocalDateTime.now(),
                status = PaymentStatus.PENDING,
                statusChangedAt = LocalDateTime.now(),
            )

        paymentRepository.create(payment)

        return paymentRequest
    }
}
