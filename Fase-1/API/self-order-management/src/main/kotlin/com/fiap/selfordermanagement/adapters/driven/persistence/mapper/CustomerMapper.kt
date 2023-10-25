package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.CustomerEntity
import com.fiap.selfordermanagement.application.domain.entities.Customer
import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer

    fun toEntity(domain: Customer): CustomerEntity
}
