package com.fiap.selfordermanagement.application.adapter.services

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.usecases.CancelOrderUseCase
import com.fiap.selfordermanagement.application.usecases.CompleteOrderUseCase
import com.fiap.selfordermanagement.application.usecases.DeleteItemsUseCase
import com.fiap.selfordermanagement.application.usecases.GetOrdersUseCase
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository

class OrderService(
    private val repository: OrderRepository,
) : CancelOrderUseCase, CompleteOrderUseCase, GetOrdersUseCase, DeleteItemsUseCase {
    override fun cancelOrder(orderId: Long): Order? {
        return repository.findById(orderId)?.let {
            cancelOrder(order = it)
        }
    }

    override fun cancelOrder(order: Order): Order {
        return repository.cancel(order)
    }

    override fun completeOrder(order: Order): Order {
        return repository.complete(order)
    }

    override fun getOrders(): List<Order> = repository.findAll()

    override fun deleteItems(order: Order): Order {
        return repository.deleteItems(order)
    }
}
