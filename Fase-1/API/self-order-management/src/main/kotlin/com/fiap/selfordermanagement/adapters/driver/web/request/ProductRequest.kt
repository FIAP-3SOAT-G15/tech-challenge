package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductType
import java.math.BigDecimal

data class ProductRequest(
    val number: Long,
    val name: String,
    val type: String,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val minSub: Int = 0,
    val maxSub: Int = Int.MAX_VALUE,
) {
    fun toDomain(): Product {
        return Product(
            number = number,
            name = name,
            type = ProductType.valueOf(type),
            price = price,
            description = description,
            category = category,
            minSub = minSub,
            maxSub = maxSub,
            subItem = arrayListOf(),
        )
    }
}
