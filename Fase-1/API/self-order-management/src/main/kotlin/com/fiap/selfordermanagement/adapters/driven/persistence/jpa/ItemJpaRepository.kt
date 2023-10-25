package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ItemEntity
import org.springframework.data.repository.CrudRepository

interface ItemJpaRepository : CrudRepository<ItemEntity, String> {
    fun findByNameContains(name: String): List<ItemEntity>
}
