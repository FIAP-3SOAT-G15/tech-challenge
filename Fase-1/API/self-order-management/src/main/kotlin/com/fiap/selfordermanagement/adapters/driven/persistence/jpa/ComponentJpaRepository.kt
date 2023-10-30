package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ComponentEntity
import org.springframework.data.repository.CrudRepository

interface ComponentJpaRepository : CrudRepository<ComponentEntity, Long> {
    fun findByNameContainingIgnoreCase(componentName: String): List<ComponentEntity>
}
