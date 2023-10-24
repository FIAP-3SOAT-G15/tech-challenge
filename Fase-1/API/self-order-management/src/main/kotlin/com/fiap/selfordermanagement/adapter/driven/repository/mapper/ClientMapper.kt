package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.ClientEntity
import com.fiap.selfordermanagement.core.domain.entities.Client
import org.mapstruct.Mapper

@Mapper
interface ClientMapper {

    fun toDomain(entity: ClientEntity) : Client

    fun toEntity(domain: Client) : ClientEntity

}