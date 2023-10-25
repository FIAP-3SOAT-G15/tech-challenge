package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Item
import com.fiap.selfordermanagement.application.ports.incoming.AssembleProductsUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.ItemRepository

class ProductService(
    private val itemRepository: ItemRepository,
) : AssembleProductsUseCase {
    override fun compose(
        itemName: String,
        subItemsName: List<String>,
    ): Item? {
        val items = subItemsName.mapNotNull(itemRepository::findById)
        return itemRepository.findById(itemName)?.let {
            it.copy(subItem = it.subItem.plus(items))
        }?.let(itemRepository::update)
    }

    override fun create(item: Item): Item = itemRepository.create(item)

    override fun update(item: Item): Item = itemRepository.update(item)

    override fun remove(itemName: String): Item = itemRepository.delete(itemName)
}
