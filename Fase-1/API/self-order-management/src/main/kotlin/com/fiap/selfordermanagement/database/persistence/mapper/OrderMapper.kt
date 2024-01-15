package com.fiap.selfordermanagement.database.persistence.mapper

import com.fiap.selfordermanagement.database.persistence.entities.OrderEntity
import com.fiap.selfordermanagement.application.domain.entities.Order
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity
}
