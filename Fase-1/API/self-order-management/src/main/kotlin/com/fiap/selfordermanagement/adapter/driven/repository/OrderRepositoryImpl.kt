package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.adapter.driven.repository.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.mapper.OrderMapper
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository
import com.fiap.selfordermanagement.core.domain.valueobjects.Status
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

    override fun searchByClient(document: String): List<Order> {
        return orderJpaRepository.findByClientDocument(document)
            .map(mapper::toDomain)
    }

    override fun searchByClient(
        document: String,
        status: Status,
    ): List<Order> {
        return orderJpaRepository.findByClientDocumentAndStatus(
            document = document,
            status = status.name,
        ).map(mapper::toDomain)
    }

    override fun searchByNickName(
        nickName: String,
        status: Status,
    ): List<Order> {
        return orderJpaRepository.findByNickNameAndStatus(nickName = nickName, status = status.name)
            .map(mapper::toDomain)
    }

    override fun searchByNickName(nickName: String): List<Order> {
        return orderJpaRepository.findByNickName(nickName = nickName)
            .map(mapper::toDomain)
    }

    override fun findById(orderId: Long): Order? {
        return orderJpaRepository.findById(orderId).map(mapper::toDomain).orElse(null)
    }
}
