package com.fiap.selfordermanagement.adapter.gateway.impl

import com.fiap.selfordermanagement.adapter.gateway.OrderGateway
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.driver.database.persistence.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.mapper.OrderMapper
import org.mapstruct.factory.Mappers
import java.util.*

class OrderGatewayImpl(
    private val orderJpaRepository: OrderJpaRepository,
) : OrderGateway {
    private val mapper = Mappers.getMapper(OrderMapper::class.java)

    override fun findAllActiveOrders(): List<Order> {
        return orderJpaRepository
            .findAllByStatusInOrderByStatusDescNumberAsc(
                setOf(
                    OrderStatus.CONFIRMED,
                    OrderStatus.PREPARING,
                    OrderStatus.COMPLETED,
                ),
            )
            .map(mapper::toDomain)
    }

    override fun findByOrderNumber(number: Long): Order? {
        return orderJpaRepository.findById(number)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun findByStatus(status: OrderStatus): List<Order> {
        return orderJpaRepository.findByStatus(status)
            .map(mapper::toDomain)
    }

    override fun findByCustomerId(customerId: UUID): List<Order> {
        return orderJpaRepository.findByCustomerId(customerId)
            .map(mapper::toDomain)
    }

    override fun findByCustomerIdAndStatus(
        customerId: UUID,
        status: OrderStatus,
    ): List<Order> {
        return orderJpaRepository.findByCustomerIdAndStatus(customerId, status)
            .map(mapper::toDomain)
    }

    override fun upsert(order: Order): Order {
        val currentOrder = order.number?.let { findByOrderNumber(number = order.number) } ?: order.copy(number = null)
        val orderUpdated =
            currentOrder
                .copy(
                    date = order.date,
                    status = order.status,
                    customer = order.customer,
                    items = order.items,
                    total = order.total,
                )
        return orderUpdated
            .let(mapper::toEntity)
            .let(orderJpaRepository::save)
            .let(mapper::toDomain)
    }

    override fun deleteAll() {
        orderJpaRepository.deleteAll()
    }
}
