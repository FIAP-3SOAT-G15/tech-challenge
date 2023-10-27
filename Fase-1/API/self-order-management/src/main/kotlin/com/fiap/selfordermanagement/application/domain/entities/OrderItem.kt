package com.fiap.selfordermanagement.application.domain.entities

import java.math.BigDecimal

data class OrderItem(
    val productNumber: Long,
    val quantity: Long,
    val price: BigDecimal,
)
