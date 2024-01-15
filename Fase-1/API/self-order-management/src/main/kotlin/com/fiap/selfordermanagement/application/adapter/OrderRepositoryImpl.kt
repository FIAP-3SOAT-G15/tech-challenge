package com.fiap.selfordermanagement.application.adapter

import com.fiap.selfordermanagement.application.adapter.mapper.OrderMapper
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.Status
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.infra.provider.jpa.OrderJpaRepository
import org.mapstruct.factory.Mappers

class OrderRepositoryImpl(private val orderJpaRepository: OrderJpaRepository) : OrderRepository {
    private val mapper = Mappers.getMapper(OrderMapper::class.java)

    override fun complete(order: Order): Order {
        return order.copy(status = Status.FINISHED)
    }

    override fun deleteItems(order: Order): Order {
        return order.copy(items = emptyList())
    }

    override fun findAll(): List<Order> {
        return emptyList()
    }

    override fun create(order: Order): Order {
        return orderJpaRepository.save(
            order.copy(id = null).let(mapper::toEntity),
        ).let(mapper::toDomain)
    }

    override fun upsert(order: Order): Order {
        val currentOrder = order.id?.let { findById(orderId = order.id) } ?: order.copy(id = null)
        return currentOrder
            .let(mapper::toEntity)
            .let(orderJpaRepository::save)
            .let(mapper::toDomain)
    }

    override fun cancel(order: Order): Order {
        var currentOrder = order.id?.let { findById(it) } ?: order
        return currentOrder.copy(status = Status.CANCELLED)
            .let(mapper::toEntity)
            .let(orderJpaRepository::save)
            .let(mapper::toDomain)
    }

    override fun searchByCustomer(document: String): List<Order> {
        return orderJpaRepository.findByCustomerDocument(document)
            .map(mapper::toDomain)
    }

    override fun searchByCustomer(
        document: String,
        status: Status,
    ): List<Order> {
        return orderJpaRepository.findByCustomerDocumentAndStatus(
            document = document,
            status = status.name,
        ).map(mapper::toDomain)
    }

    override fun searchByNickname(
        nickname: String,
        status: Status,
    ): List<Order> {
        return orderJpaRepository.findByNicknameAndStatus(nickname = nickname, status = status.name)
            .map(mapper::toDomain)
    }

    override fun searchByNickname(nickname: String): List<Order> {
        return orderJpaRepository.findByNickname(nickname = nickname)
            .map(mapper::toDomain)
    }

    override fun findById(orderId: Long): Order? {
        return orderJpaRepository.findById(orderId).map(mapper::toDomain).orElse(null)
    }
}
