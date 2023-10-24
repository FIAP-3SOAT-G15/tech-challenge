package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.mapper.ItemMapper
import com.fiap.selfordermanagement.core.domain.entities.Item
import com.fiap.selfordermanagement.core.domain.errors.ErrorType
import com.fiap.selfordermanagement.core.domain.errors.SelfOrderManagementException
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

    override fun create(item: Item): Item {
        findById(item.name)?.let {
            throw SelfOrderManagementException(
                    errorType = ErrorType.ITEM_ALREADY_EXISTS, message = "Item ${item.name} already exists")
        }
        return persist(item)
    }

    override fun update(item: Item): Item {
        val newItem = findById(item.name)
            ?.let { it.update(item) }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.ITEM_NOT_EXISTS, message = "Item ${item.name} not exists")
        return persist(newItem)
    }

    override fun delete(itemName: String): Item {
        return findById(itemName)?.let {
            itemJpaRepository.deleteById(itemName)
            it
        } ?:
        throw SelfOrderManagementException(errorType = ErrorType.ITEM_NOT_EXISTS, message = "Item $itemName not exists")
    }

    private fun persist(item: Item) : Item = item
        .let(mapper::toEntity)
        .let(itemJpaRepository::save)
        .let(mapper::toDomain)
}