package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.InputEntity
import org.springframework.data.repository.CrudRepository

interface InputJpaRepository : CrudRepository<InputEntity, Long> {
    fun findByNameContainingOrderByName(name: String): List<InputEntity>
}
