package com.fiap.selfordermanagement.database.persistence.jpa

import com.fiap.selfordermanagement.database.persistence.entities.ComponentEntity
import org.springframework.data.repository.CrudRepository

interface ComponentJpaRepository : CrudRepository<ComponentEntity, Long> {
    fun findByNameContainingIgnoreCase(componentName: String): List<ComponentEntity>
}
