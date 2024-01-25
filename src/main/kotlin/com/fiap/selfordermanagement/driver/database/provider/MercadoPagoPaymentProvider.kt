package com.fiap.selfordermanagement.driver.database.provider

import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import java.math.BigDecimal
import java.util.*

class MercadoPagoPaymentProvider : PaymentProviderGateway {
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
