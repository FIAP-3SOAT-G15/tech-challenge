package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ProductEntity
import com.fiap.selfordermanagement.application.domain.entities.Product
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper
interface ProductMapper {
    @Mapping(
        source = "subItem",
        target = "subItem",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    )
    fun toDomain(entity: ProductEntity): Product

    @Mapping(
        source = "subItem",
        target = "subItem",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    )
    fun toEntity(domain: Product): ProductEntity
}
