package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.mapper.ItemMapper
import com.fiap.selfordermanagement.core.domain.entities.Item
import com.fiap.selfordermanagement.core.domain.repositories.ItemRepository
import org.mapstruct.factory.Mappers

class ItemRepositoryImpl(private val itemJpaRepository: ItemJpaRepository): ItemRepository {

    private val mapper: ItemMapper = Mappers.getMapper(ItemMapper::class.java)

    override fun findById(id: String): Item? {
        return itemJpaRepository.findById(id).map { mapper.toDomain(it) }.orElse(null)
    }

    override fun searchByName(name: String): List<Item> {
        return itemJpaRepository.findByNameContains(name)
            .map(mapper::toDomain)
    }

    override fun upsert(item: Item): Item {
        val newItem = findById(item.name)?.let { it.update(item) } ?: item
        return newItem
            .let(mapper::toEntity)
            .let(itemJpaRepository::save)
            .let(mapper::toDomain)

    }

    override fun delete(item: Item): Item {
        itemJpaRepository.delete(mapper.toEntity(item))
        return item
    }
}