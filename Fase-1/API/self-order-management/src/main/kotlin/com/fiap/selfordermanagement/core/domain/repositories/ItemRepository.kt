package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Item

interface ItemRepository {

    fun findById(name: String) : Item?

    fun searchByName(name: String) : List<Item>

    fun upsert(item: Item) : Item

    fun delete(item: Item): Item
}