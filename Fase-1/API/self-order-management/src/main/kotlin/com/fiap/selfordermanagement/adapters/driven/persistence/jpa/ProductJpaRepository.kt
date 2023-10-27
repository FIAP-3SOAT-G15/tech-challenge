package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ProductEntity
import org.springframework.data.repository.CrudRepository

interface ProductJpaRepository : CrudRepository<ProductEntity, Long> {
    fun findByNameContains(name: String): List<ProductEntity>
}
