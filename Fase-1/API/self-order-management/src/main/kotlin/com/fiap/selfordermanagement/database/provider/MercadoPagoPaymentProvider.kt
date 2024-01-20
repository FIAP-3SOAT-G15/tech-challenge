package com.fiap.selfordermanagement.database.provider

import com.fiap.selfordermanagement.application.adapter.repository.PaymentProvider
import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.application.domain.valueobjects.PaymentStatus
import java.math.BigDecimal
import java.util.UUID

class MercadoPagoPaymentProvider : PaymentProvider {
    override fun createPaymentRequest(
        internalReference: String,
        amount: BigDecimal,
    ): PaymentRequest {
        return PaymentRequest(
            externalId = UUID.randomUUID(),
            qrCode = "Close your eyes and imagine a QR image encoded in Base64 format",
        )
    }

    override fun getPaymentRequest(externalPaymentId: UUID): PaymentRequest {
        return PaymentRequest(
            externalId = externalPaymentId,
            qrCode = "Let's pretend the QR code has changed",
        )
    }

    override fun verifyPaymentStatus(externalPaymentId: UUID): PaymentStatus {
        // always confirming
        return PaymentStatus.CONFIRMED
    }
}
