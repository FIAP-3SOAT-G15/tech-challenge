package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.ItemEntity
import com.fiap.selfordermanagement.application.domain.entities.Item
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper
interface ItemMapper {
    @Mapping(
        source = "subItem",
        target = "subItem",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT,
    )
    fun toDomain(entity: ItemEntity): Item

    @Mapping(
        source = "subItem",
        target = "subItem",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    )
    fun toEntity(domain: Item): ItemEntity
}