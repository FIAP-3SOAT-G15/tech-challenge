package com.fiap.selfordermanagement.database.persistence.mapper

import com.fiap.selfordermanagement.database.persistence.entities.PaymentEntity
import com.fiap.selfordermanagement.application.domain.entities.Payment
import org.mapstruct.Mapper

@Mapper
interface PaymentMapper {
    fun toDomain(entity: PaymentEntity): Payment

    fun toEntity(domain: Payment): PaymentEntity
}
