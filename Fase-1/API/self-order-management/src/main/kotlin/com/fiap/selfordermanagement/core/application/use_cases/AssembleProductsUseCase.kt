package com.fiap.selfordermanagement.core.application.use_cases

import com.fiap.selfordermanagement.core.domain.entities.Item

interface AssembleProductsUseCase {
    fun compose(itemName: String, subItems: List<Item>) : Item?
    fun create(item: Item): Item
}