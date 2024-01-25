package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus

interface LoadOrderUseCase {
    fun getByOrderNumber(orderNumber: Long): Order

    fun findAll(): List<Order>

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
}
