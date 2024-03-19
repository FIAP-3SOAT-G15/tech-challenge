package com.fiap.selfordermanagement.driver.database.persistence.jpa

import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.driver.database.persistence.entities.OrderEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrderJpaRepository : CrudRepository<OrderEntity, Long> {
    fun findByStatus(status: OrderStatus): List<OrderEntity>

    fun findByCustomerId(customerId: UUID): List<OrderEntity>

    fun findByCustomerIdAndStatus(
        customerId: UUID,
        status: OrderStatus,
    ): List<OrderEntity>

    fun findAllByStatusInOrderByStatusDescNumberAsc(status: Set<OrderStatus>): List<OrderEntity>
}
