package com.fiap.selfordermanagement.driver.database.persistence.mapper

import com.fiap.selfordermanagement.domain.entities.Customer
import com.fiap.selfordermanagement.driver.database.persistence.entities.CustomerEntity
import org.mapstruct.Mapper

@Mapper
interface CustomerMapper {
    fun toDomain(entity: CustomerEntity): Customer

    fun toEntity(domain: Customer): CustomerEntity
}
