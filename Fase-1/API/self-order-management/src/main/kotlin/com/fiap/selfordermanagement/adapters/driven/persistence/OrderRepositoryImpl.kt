package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.mapper.OrderMapper
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import org.mapstruct.factory.Mappers

class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository,
) : OrderRepository {
    private val mapper = Mappers.getMapper(OrderMapper::class.java)

    override fun findAll(): List<Order> {
        return orderJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun findByOrderNumber(number: Long): Order? {
        return orderJpaRepository.findById(number)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun findByStatus(status: OrderStatus): List<Order> {
        return orderJpaRepository.findByStatus(status.toString())
            .map(mapper::toDomain)
    }

    override fun findByCustomerNickname(nickname: String): List<Order> {
        return orderJpaRepository.findByCustomerNickname(nickname)
            .map(mapper::toDomain)
    }

    override fun findByCustomerNicknameAndStatus(
        nickname: String,
        status: OrderStatus,
    ): List<Order> {
        return orderJpaRepository.findByCustomerNicknameAndStatus(nickname, status.toString())
            .map(mapper::toDomain)
    }

    override fun findByCustomerDocument(document: String): List<Order> {
        return orderJpaRepository.findByCustomerDocument(document)
            .map(mapper::toDomain)
    }

    override fun findByCustomerDocumentAndStatus(
        document: String,
        status: OrderStatus,
    ): List<Order> {
        return orderJpaRepository.findByCustomerDocumentAndStatus(
            document = document,
            status = status.name,
        ).map(mapper::toDomain)
    }

    override fun upsert(order: Order): Order {
        val currentOrder = order.number?.let { findByOrderNumber(number = order.number) } ?: order.copy(number = null)
        val orderUpdated =
            currentOrder
                .copy(
                    date = order.date,
                    status = order.status,
                    customer = order.customer,
                    customerNickname = order.customerNickname,
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
