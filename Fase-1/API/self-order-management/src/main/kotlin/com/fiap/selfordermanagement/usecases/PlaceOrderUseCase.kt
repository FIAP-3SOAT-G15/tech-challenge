package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.OrderItem

interface PlaceOrderUseCase {
    fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItem>,
    ): Order
}
