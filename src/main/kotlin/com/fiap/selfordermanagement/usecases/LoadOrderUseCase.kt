package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import java.util.*

interface LoadOrderUseCase {
    fun getByOrderNumber(orderNumber: Long): Order

    fun findAll(): List<Order>

    fun findByStatus(status: OrderStatus): List<Order>

    fun findByCustomerId(customerId: UUID): List<Order>

    fun findByCustomerIdAndStatus(
        customerId: UUID,
        status: OrderStatus,
    ): List<Order>
}
