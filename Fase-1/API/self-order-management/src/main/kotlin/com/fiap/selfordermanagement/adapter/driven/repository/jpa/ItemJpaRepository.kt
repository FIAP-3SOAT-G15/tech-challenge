package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.ItemEntity
import org.springframework.data.repository.CrudRepository

interface ItemJpaRepository : CrudRepository<ItemEntity, String> {

    fun findByNameContains(name: String) : List<ItemEntity>
}