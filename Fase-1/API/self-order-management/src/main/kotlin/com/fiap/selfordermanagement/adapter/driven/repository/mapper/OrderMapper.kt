package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.OrdersEntity
import com.fiap.selfordermanagement.core.domain.entities.Order
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrdersEntity): Order

    fun toEntity(domain: Order): OrdersEntity
}
