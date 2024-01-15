package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.OrderItem

interface PlaceOrderUseCase {
    fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItem>,
    ): Order
}
