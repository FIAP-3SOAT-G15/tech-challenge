package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.OrderEntity
import org.springframework.data.repository.CrudRepository

interface OrderJpaRepository : CrudRepository<OrderEntity, Long> {
    fun findByCustomerName(customerName: String): List<OrderEntity>

    fun findByCustomerDocument(document: String): List<OrderEntity>

    fun findByCustomerDocumentAndStatus(
        document: String,
        status: String,
    ): List<OrderEntity>

    fun findByNicknameAndStatus(
        nickname: String,
        status: String,
    ): List<OrderEntity>

    fun findByNickname(nickname: String): List<OrderEntity>
}
