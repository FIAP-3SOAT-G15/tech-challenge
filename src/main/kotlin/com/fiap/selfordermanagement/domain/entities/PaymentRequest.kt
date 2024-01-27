package com.fiap.selfordermanagement.domain.entities

class PaymentRequest(
    val externalOrderId: String,
    val externalOrderGlobalId: String?,
    val paymentInfo: String,
)
