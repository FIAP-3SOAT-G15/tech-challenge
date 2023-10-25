package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.OrderEntity
import com.fiap.selfordermanagement.application.domain.entities.Order
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity
}
