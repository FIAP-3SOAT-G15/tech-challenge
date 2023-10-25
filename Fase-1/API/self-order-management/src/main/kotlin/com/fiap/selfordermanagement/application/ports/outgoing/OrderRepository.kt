package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.Status

interface OrderRepository {
    fun complete(order: Order): Order

    fun deleteItems(order: Order): Order

    fun findAll(): List<Order>

    fun create(order: Order): Order

    fun upsert(order: Order): Order

    fun cancel(order: Order): Order

    fun searchByCustomer(document: String): List<Order>

    fun searchByCustomer(
        document: String,
        status: Status,
    ): List<Order>

    fun searchByNickname(
        nickname: String,
        status: Status,
    ): List<Order>

    fun searchByNickname(nickname: String): List<Order>

    fun findById(orderId: Long): Order?
}
