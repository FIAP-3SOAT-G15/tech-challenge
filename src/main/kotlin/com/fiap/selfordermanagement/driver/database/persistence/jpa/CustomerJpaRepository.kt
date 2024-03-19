package com.fiap.selfordermanagement.driver.database.persistence.jpa

import com.fiap.selfordermanagement.driver.database.persistence.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CustomerJpaRepository : CrudRepository<CustomerEntity, String> {
    fun findByEmail(email: String): Optional<CustomerEntity>

    fun findByDocument(document: String): Optional<CustomerEntity>

    fun findByNameContainingIgnoreCase(customerName: String): List<CustomerEntity>
}
