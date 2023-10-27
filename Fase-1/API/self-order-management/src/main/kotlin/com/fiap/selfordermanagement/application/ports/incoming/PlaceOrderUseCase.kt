package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.adapters.driver.web.request.OrderItemRequest
import com.fiap.selfordermanagement.application.domain.entities.Order

interface PlaceOrderUseCase {
    fun create(
        customerNickname: String,
        customerDocument: String?,
        items: List<OrderItemRequest>,
    ): Order
}
