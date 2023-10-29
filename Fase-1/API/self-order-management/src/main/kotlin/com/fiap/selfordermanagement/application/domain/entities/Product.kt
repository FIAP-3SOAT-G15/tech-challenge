package com.fiap.selfordermanagement.application.domain.entities

import com.fiap.selfordermanagement.application.domain.valueobjects.ProductType
import java.math.BigDecimal

data class Product(
    val number: Long? = null,
    val name: String,
    val type: ProductType,
    val price: BigDecimal,
    val description: String,
    val category: String,
    val minSub: Int,
    val maxSub: Int,
    val subItem: List<Product>,
    val inputs: List<Input>,
) {
    fun update(product: Product): Product =
        copy(
            name = product.name,
            type = product.type,
            price = product.price,
            description = product.description,
            category = product.category,
            subItem = product.subItem,
            maxSub = product.maxSub,
            minSub = product.minSub,
            inputs = product.inputs,
        )

    fun isLogicalItem() = inputs.isEmpty()
}
