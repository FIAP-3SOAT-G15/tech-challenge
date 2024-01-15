package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface CancelOrderUseCase {
    fun cancelOrder(orderId: Long): Order?

    fun cancelOrder(order: Order): Order
}
