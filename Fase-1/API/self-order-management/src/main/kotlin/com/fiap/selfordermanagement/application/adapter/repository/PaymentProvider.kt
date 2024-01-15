package com.fiap.selfordermanagement.application.adapter.repository

import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import java.math.BigDecimal
import java.util.UUID

interface PaymentProvider {
    fun getPaymentRequest(externalPaymentId: UUID): PaymentRequest

    fun createPaymentRequest(
        internalReference: String,
        amount: BigDecimal,
    ): PaymentRequest

    fun verifyPaymentStatus(externalPaymentId: UUID): PaymentStatus
}
