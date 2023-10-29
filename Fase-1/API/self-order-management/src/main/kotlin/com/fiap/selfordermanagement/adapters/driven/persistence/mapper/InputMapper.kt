package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.InputEntity
import com.fiap.selfordermanagement.application.domain.entities.Input
import org.mapstruct.Mapper

@Mapper
interface InputMapper {
    fun toDomain(entity: InputEntity): Input

    fun toEntity(domain: Input): InputEntity
}
