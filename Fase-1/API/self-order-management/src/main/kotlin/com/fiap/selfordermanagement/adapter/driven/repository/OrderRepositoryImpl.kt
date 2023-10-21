package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository
import com.fiap.selfordermanagement.core.domain.value_objects.Status

class OrderRepositoryImpl : OrderRepository {
    override fun complete(order: Order): Order {
        return order.copy(status = Status.FINISHED)
    }

    override fun deleteItems(order: Order): Order {
        return order.copy(items = emptyList())
    }
}