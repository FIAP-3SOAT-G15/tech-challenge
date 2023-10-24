package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Order

interface CompleteOrderUseCase {
    fun execute(order: Order): Order
}
