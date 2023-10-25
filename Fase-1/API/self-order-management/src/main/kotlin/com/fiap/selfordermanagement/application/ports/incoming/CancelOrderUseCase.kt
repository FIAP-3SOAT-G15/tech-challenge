package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order

interface CancelOrderUseCase {
    fun cancelOrder(orderId: Long): Order?

    fun cancelOrder(order: Order): Order
}
