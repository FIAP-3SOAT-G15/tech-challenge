package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Item

interface AssembleProductsUseCase {
    fun compose(
        itemName: String,
        subItemsName: List<String>,
    ): Item?

    fun create(item: Item): Item

    fun update(item: Item): Item

    fun remove(itemName: String): Item
}
