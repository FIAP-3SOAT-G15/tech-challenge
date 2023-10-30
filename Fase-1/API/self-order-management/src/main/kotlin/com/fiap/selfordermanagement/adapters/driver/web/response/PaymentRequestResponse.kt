package com.fiap.selfordermanagement.adapters.driver.web.response

import io.swagger.v3.oas.annotations.media.Schema

data class PaymentRequestResponse(
    @Schema(title = "QR code (Base64)", required = true)
    val qrCode: String,
)
