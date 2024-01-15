package com.fiap.selfordermanagement.database.persistence.jpa

import com.fiap.selfordermanagement.database.persistence.entities.ProductEntity
import org.springframework.data.repository.CrudRepository

interface ProductJpaRepository : CrudRepository<ProductEntity, Long> {
    fun findByNameContainingIgnoreCase(productName: String): List<ProductEntity>

    fun findByCategoryIgnoreCase(category: String): List<ProductEntity>
}
