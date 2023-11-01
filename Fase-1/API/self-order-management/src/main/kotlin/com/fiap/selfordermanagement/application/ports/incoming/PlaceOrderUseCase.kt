package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.OrderItem

interface PlaceOrderUseCase {
    fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItem>,
    ): Order
}
