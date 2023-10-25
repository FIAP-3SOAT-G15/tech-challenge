package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Item
import com.fiap.selfordermanagement.application.domain.valueobjects.ItemType
import java.math.BigDecimal

class ItemRequest(
    private val name: String,
    private val type: String,
    private val price: BigDecimal,
    private val description: String,
    private val category: String,
    private val minSub: Int = 0,
    private val maxSub: Int = Int.MAX_VALUE,
) {
    fun toDomain(): Item {
        return Item(
            name = name,
            type = ItemType.valueOf(type),
            price = price,
            description = description,
            category = category,
            minSub = minSub,
            maxSub = maxSub,
            subItem = arrayListOf(),
        )
    }
}
