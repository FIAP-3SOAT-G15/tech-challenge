package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.ItemEntity
import com.fiap.selfordermanagement.core.domain.entities.Item
import org.mapstruct.Mapper

@Mapper
interface ItemMapper {

    fun toDomain(entity: ItemEntity) : Item

    fun toEntity(domain: Item) : ItemEntity

}