package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus

interface OrderRepository {
    fun findAll(): List<Order>

    fun findByOrderNumber(number: Long): Order?

    fun findByStatus(status: OrderStatus): List<Order>

    fun findByCustomerNickname(nickname: String): List<Order>

    fun findByCustomerNicknameAndStatus(
        nickname: String,
        status: OrderStatus,
    ): List<Order>

    fun findByCustomerDocument(document: String): List<Order>

    fun findByCustomerDocumentAndStatus(
        document: String,
        status: OrderStatus,
    ): List<Order>

    fun upsert(order: Order): Order

    fun deleteAll()
}
