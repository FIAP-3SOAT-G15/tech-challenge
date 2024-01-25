package com.fiap.selfordermanagement.domain.entities

import java.util.UUID

class PaymentRequest(
    val externalId: UUID,
    val qrCode: String,
)
