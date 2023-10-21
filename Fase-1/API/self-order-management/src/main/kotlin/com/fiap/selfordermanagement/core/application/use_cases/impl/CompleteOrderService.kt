package com.fiap.selfordermanagement.core.application.use_cases.impl

import com.fiap.selfordermanagement.core.application.use_cases.CompleteOrderUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class CompleteOrderService(private val repository: OrderRepository) : CompleteOrderUseCase {
    override fun execute(order: Order): Order {
        return repository.complete(order)
    }
}