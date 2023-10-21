package com.fiap.selfordermanagement.core.application.use_cases.impl

import com.fiap.selfordermanagement.core.application.use_cases.AssembleProductsUseCase
import com.fiap.selfordermanagement.core.domain.entities.Item
import com.fiap.selfordermanagement.core.domain.repositories.ItemRepository

class AssembleProductsService(
    private val itemRepository: ItemRepository
) : AssembleProductsUseCase {

    override fun compose(itemName: String, subItems: List<Item>): Item? {
        return itemRepository.findById(itemName)?.let {
            it.copy(subItem = it.subItem.plus(subItems))
        }?.let(itemRepository::upsert)

    }

    override fun create(item: Item): Item {
        return itemRepository.upsert(item)
    }

}