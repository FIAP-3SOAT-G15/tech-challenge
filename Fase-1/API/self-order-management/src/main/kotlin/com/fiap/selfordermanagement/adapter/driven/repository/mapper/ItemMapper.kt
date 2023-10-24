package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.ItemEntity
import com.fiap.selfordermanagement.core.domain.entities.Item
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
