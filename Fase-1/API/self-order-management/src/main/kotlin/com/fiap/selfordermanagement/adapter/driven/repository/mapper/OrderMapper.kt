package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.OrderEntity
import com.fiap.selfordermanagement.core.domain.entities.Order
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity
}
