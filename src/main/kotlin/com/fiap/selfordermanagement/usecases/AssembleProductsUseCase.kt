package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Product

interface AssembleProductsUseCase {
    fun compose(
        productNumber: Long,
        subItemsNumbers: List<Long>,
    ): Product?

    fun create(
        product: Product,
        components: List<Long>,
    ): Product

    fun update(
        product: Product,
        components: List<Long>,
    ): Product

    fun delete(productNumber: Long): Product
}
