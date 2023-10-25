package com.fiap.selfordermanagement.application.domain.entities

import com.fiap.selfordermanagement.application.domain.valueobjects.ItemType
import java.math.BigDecimal

data class Item(
    val name: String,
    val type: ItemType,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val minSub: Int,
    val maxSub: Int,
    val subItem: List<Item>,
) {
    fun update(item: Item): Item {
        return copy(
            type = item.type,
            price = item.price,
            description = item.description,
            category = item.category,
            subItem = item.subItem,
            maxSub = item.maxSub,
            minSub = item.minSub,
        )
    }
}
