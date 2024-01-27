package com.fiap.selfordermanagement.domain.entities

import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import java.time.LocalDateTime

data class Payment(
    val orderNumber: Long,
    val externalOrderId: String,
    val externalOrderGlobalId: String?,
    val paymentInfo: String,
    val createdAt: LocalDateTime,
    val status: PaymentStatus,
    val statusChangedAt: LocalDateTime,
) {
    fun update(newPayment: Payment): Payment =
        copy(
            orderNumber = newPayment.orderNumber,
            externalOrderId = newPayment.externalOrderId,
            externalOrderGlobalId = newPayment.externalOrderGlobalId,
            paymentInfo = newPayment.paymentInfo,
            createdAt = newPayment.createdAt,
            status = newPayment.status,
            statusChangedAt = newPayment.statusChangedAt,
        )
}
