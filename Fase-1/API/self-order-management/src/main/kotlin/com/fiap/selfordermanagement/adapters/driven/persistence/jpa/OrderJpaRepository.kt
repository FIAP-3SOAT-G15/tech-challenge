package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.OrderEntity
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import org.springframework.data.repository.CrudRepository

interface OrderJpaRepository : CrudRepository<OrderEntity, Long> {
    fun findByStatus(status: OrderStatus): List<OrderEntity>

    fun findByCustomerNickname(nickname: String): List<OrderEntity>

    fun findByCustomerNicknameAndStatus(
        nickname: String,
        status: String,
    ): List<OrderEntity>

    fun findByCustomerDocument(document: String): List<OrderEntity>

    fun findByCustomerDocumentAndStatus(
        document: String,
        status: String,
    ): List<OrderEntity>

    fun findAllByStatusInOrderByStatusDescNumberAsc(status: Set<OrderStatus>): List<OrderEntity>
}
