package com.fiap.selfordermanagement.core.application.use_cases.impl

import com.fiap.selfordermanagement.core.application.use_cases.CancelOrderUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class CancelOrderService(
    private val orderRepository: OrderRepository
) : CancelOrderUseCase {

    override fun withId(orderId: Long): Order? {
        return orderRepository.findById(orderId)?.let {
            withOrder(order = it)
        }
    }

    override fun withOrder(order: Order): Order {
        return orderRepository.cancel(order)
    }
}