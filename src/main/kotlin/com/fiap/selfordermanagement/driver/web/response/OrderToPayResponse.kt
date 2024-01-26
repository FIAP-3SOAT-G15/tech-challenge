package com.fiap.selfordermanagement.driver.web.response

import com.fiap.selfordermanagement.domain.entities.Order

data class OrderToPayResponse(
    val order: Order,
    val paymentInfo: String,
)
