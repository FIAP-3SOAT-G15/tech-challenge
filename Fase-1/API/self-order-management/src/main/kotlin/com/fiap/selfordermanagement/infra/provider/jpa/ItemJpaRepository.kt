package com.fiap.selfordermanagement.infra.provider.jpa

import com.fiap.selfordermanagement.infra.provider.persistence.entities.ItemEntity
import org.springframework.data.repository.CrudRepository

interface ItemJpaRepository :
    CrudRepository<ItemEntity, String> {
    fun findByNameContains(name: String): List<ItemEntity>
}
