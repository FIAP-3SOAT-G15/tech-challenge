package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Item

interface ItemRepository {

    fun findById(name: String) : Item?

    fun searchByName(name: String) : List<Item>

    fun create(item: Item) : Item

    fun update(item: Item) : Item

    fun delete(itemName: String): Item
}