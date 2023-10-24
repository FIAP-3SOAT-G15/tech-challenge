package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.CustomerEntity
import com.fiap.selfordermanagement.core.domain.entities.Customer
import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer

    fun toEntity(domain: Customer): CustomerEntity
}
