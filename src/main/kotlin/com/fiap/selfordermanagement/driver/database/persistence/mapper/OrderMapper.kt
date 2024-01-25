package com.fiap.selfordermanagement.driver.database.persistence.mapper

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.driver.database.persistence.entities.OrderEntity
import org.mapstruct.Mapper

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity
}
