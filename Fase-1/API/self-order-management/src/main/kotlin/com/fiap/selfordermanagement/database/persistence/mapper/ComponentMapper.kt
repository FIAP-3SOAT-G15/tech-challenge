package com.fiap.selfordermanagement.database.persistence.mapper

import com.fiap.selfordermanagement.database.persistence.entities.ComponentEntity
import com.fiap.selfordermanagement.application.domain.entities.Component
import org.mapstruct.Mapper

@Mapper
interface ComponentMapper {
    fun toDomain(entity: ComponentEntity): Component

    fun toEntity(domain: Component): ComponentEntity
}
