package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order

interface UpsertOrderUseCase {
    fun upsertOrder(order: Order): Order
}
