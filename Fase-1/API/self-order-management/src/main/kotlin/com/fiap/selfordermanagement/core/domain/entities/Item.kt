package com.fiap.selfordermanagement.core.domain.entities

import com.fiap.selfordermanagement.core.domain.value_objects.ItemType

data class Item(val name: String, val description: String, val price: Double, val type: ItemType)
