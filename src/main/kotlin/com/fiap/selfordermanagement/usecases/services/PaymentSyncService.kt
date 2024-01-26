package com.fiap.selfordermanagement.usecases.services

import com.fiap.selfordermanagement.adapter.gateway.PaymentGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import com.fiap.selfordermanagement.usecases.ConfirmOrderUseCase
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import com.fiap.selfordermanagement.usecases.SyncPaymentUseCase
import java.time.LocalDateTime

class PaymentSyncService(
    private val confirmOrderUseCase: ConfirmOrderUseCase,
    private val loadPaymentUseCase: LoadPaymentUseCase,
    private val paymentGateway: PaymentGateway,
    private val paymentProviderGateway: PaymentProviderGateway,
): SyncPaymentUseCase {

    override fun syncPayment(orderNumber: Long, externalOrderGlobalId: String) {
        val payment = loadPaymentUseCase.getByOrderNumber(orderNumber)

        if (payment.externalOrderGlobalId == null) {
            paymentGateway.update(payment.copy(externalOrderGlobalId = externalOrderGlobalId))
        }

        val newStatus = paymentProviderGateway.checkExternalOrderStatus(externalOrderGlobalId)

        if (payment.status != newStatus) {
            paymentGateway.update(
                payment.copy(
                    status = newStatus,
                    statusChangedAt = LocalDateTime.now(),
                )
            )

            if (newStatus == PaymentStatus.CONFIRMED) {
                confirmOrderUseCase.confirmOrder(orderNumber)
            }
        }
    }
}
