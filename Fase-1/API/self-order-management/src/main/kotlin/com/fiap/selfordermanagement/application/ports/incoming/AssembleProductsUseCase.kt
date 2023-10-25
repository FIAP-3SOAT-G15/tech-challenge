package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Item

interface AssembleProductsUseCase {
    fun compose(
        itemName: String,
        subItemsName: List<String>,
    ): Item?

    fun create(item: Item): Item

    fun update(item: Item): Item

    fun remove(itemName: String): Item
}
