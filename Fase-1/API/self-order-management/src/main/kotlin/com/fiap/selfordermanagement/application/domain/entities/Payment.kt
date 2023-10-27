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
    fun update(payment: Payment): Payment =
        copy(
            orderNumber = payment.orderNumber,
            externalId = payment.externalId,
            createdAt = payment.createdAt,
            status = payment.status,
            statusChangedAt = payment.statusChangedAt,
        )
}
