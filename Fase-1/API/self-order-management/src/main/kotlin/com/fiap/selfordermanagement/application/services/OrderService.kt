package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Item
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.ports.incoming.*
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository

class OrderService(
    private val repository: OrderRepository,
) : CancelOrderUseCase, CompleteOrderUseCase, GetOrdersUseCase, DeleteItemsUseCase, UpsertItemsUseCase,
    UpsertOrderUseCase {
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

    override fun upsertItem(order: Order, item: Item): Order {
        return repository.upsert(order.copy(items = order.items.plus(item)))
    }

    override fun upsertOrder(order: Order): Order {
        return repository.upsert(order)
    }
}
