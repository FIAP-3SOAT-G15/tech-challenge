package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order

interface CompleteOrderUseCase {
    fun completeOrder(order: Order): Order
}
