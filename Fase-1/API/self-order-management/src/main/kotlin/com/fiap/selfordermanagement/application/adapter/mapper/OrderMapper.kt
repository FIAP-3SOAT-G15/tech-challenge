package com.fiap.selfordermanagement.application.adapter.mapper

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.infra.provider.persistence.entities.OrderEntity
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity
}
