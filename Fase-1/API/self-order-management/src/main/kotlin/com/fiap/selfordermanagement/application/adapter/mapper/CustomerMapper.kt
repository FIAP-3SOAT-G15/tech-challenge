package com.fiap.selfordermanagement.application.adapter.mapper

import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.infra.provider.persistence.entities.CustomerEntity
import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer

    fun toEntity(domain: Customer): CustomerEntity
}
