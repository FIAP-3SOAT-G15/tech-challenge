package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.OrdersEntity
import org.springframework.data.repository.CrudRepository

interface OrderJpaRepository : CrudRepository<OrdersEntity, Long> {
    fun findByCustomerName(customerName: String): List<OrdersEntity>

    fun findByCustomerDocument(document: String): List<OrdersEntity>

    fun findByCustomerDocumentAndStatus(
        document: String,
        status: String,
    ): List<OrdersEntity>

    fun findByNickNameAndStatus(
        nickName: String,
        status: String,
    ): List<OrdersEntity>

    fun findByNickName(nickName: String): List<OrdersEntity>
}
