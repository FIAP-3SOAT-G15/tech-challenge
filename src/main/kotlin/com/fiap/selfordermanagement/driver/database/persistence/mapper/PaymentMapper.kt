package com.fiap.selfordermanagement.driver.database.persistence.mapper

import com.fiap.selfordermanagement.domain.entities.Payment
import com.fiap.selfordermanagement.driver.database.persistence.entities.PaymentEntity
import org.mapstruct.Mapper

@Mapper
interface PaymentMapper {
    fun toDomain(entity: PaymentEntity): Payment

    fun toEntity(domain: Payment): PaymentEntity
}
