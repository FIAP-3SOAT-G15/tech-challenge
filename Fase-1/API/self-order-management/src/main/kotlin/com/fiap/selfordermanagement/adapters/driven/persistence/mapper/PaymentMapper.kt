package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.PaymentEntity
import com.fiap.selfordermanagement.application.domain.entities.Payment
import org.mapstruct.Mapper

@Mapper
interface PaymentMapper {
    fun toDomain(entity: PaymentEntity): Payment

    fun toEntity(domain: Payment): PaymentEntity
}
