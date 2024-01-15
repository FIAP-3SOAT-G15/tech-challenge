package com.fiap.selfordermanagement.database.persistence.jpa

import com.fiap.selfordermanagement.database.persistence.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository

interface CustomerJpaRepository : CrudRepository<CustomerEntity, String> {
    fun findByNameContainingIgnoreCase(customerName: String): List<CustomerEntity>
}
