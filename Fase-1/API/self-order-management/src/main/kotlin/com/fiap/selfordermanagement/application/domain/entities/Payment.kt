package com.fiap.selfordermanagement.application.domain.entities

import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import java.time.LocalDateTime
import java.util.UUID

data class Payment(
    val orderNumber: Long,
    val externalId: UUID,
    val createdAt: LocalDateTime,
    val status: PaymentStatus,
    val statusChangedAt: LocalDateTime,
) {
    fun update(newPayment: Payment): Payment =
        copy(
            orderNumber = newPayment.orderNumber,
            externalId = newPayment.externalId,
            createdAt = newPayment.createdAt,
            status = newPayment.status,
            statusChangedAt = newPayment.statusChangedAt,
        )
}
