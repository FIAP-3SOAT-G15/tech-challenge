package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Product

interface AssembleProductsUseCase {
    fun compose(
        productNumber: Long,
        subItemsName: List<Long>,
    ): Product?

    fun create(product: Product): Product

    fun update(product: Product): Product

    fun delete(productNumber: Long): Product
}
