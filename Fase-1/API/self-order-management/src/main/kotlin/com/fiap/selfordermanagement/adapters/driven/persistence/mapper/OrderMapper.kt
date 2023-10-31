package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.OrderEntity
import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ProductEntity
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.OrderItem
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface OrderMapper {
    fun toDomain(entity: OrderEntity): Order

    fun toEntity(domain: Order): OrderEntity

    @Mapping(target = "name", constant = "")
    @Mapping(target = "category", constant = "")
    @Mapping(target = "price", constant = "0.0")
    @Mapping(target = "components", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "subItems", expression = "java(java.util.Collections.emptyList())")
    @Mapping(target = "minSub", constant = "0")
    @Mapping(target = "maxSub", constant = "0")
    fun orderItemToProductEntity(domain: OrderItem): ProductEntity
}
