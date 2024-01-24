package com.fiap.selfordermanagement.driver.database.persistence.mapper

import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.driver.database.persistence.entities.ComponentEntity
import org.mapstruct.Mapper

@Mapper
interface ComponentMapper {
    fun toDomain(entity: ComponentEntity): Component

    fun toEntity(domain: Component): ComponentEntity
}
