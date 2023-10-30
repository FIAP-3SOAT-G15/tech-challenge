package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ComponentEntity
import com.fiap.selfordermanagement.application.domain.entities.Component
import org.mapstruct.Mapper

@Mapper
interface ComponentMapper {
    fun toDomain(entity: ComponentEntity): Component

    fun toEntity(domain: Component): ComponentEntity
}
