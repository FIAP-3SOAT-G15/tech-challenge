package com.fiap.selfordermanagement.adapter.gateway

import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import java.math.BigDecimal
import java.util.*

interface PaymentProviderGateway {
    fun createPaymentRequest(
        internalReference: String,
        amount: BigDecimal,
    ): PaymentRequest

    fun getPaymentRequest(externalPaymentId: UUID): PaymentRequest

    fun verifyPaymentStatus(externalPaymentId: UUID): PaymentStatus
}
