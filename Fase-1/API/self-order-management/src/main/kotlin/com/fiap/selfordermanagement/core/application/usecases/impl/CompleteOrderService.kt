package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.CompleteOrderUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class CompleteOrderService(private val repository: OrderRepository) : CompleteOrderUseCase {
    override fun execute(order: Order): Order {
        return repository.complete(order)
    }
}
