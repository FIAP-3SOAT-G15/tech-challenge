package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Product
import com.fiap.selfordermanagement.domain.valueobjects.ProductCategory

interface LoadProductUseCase {
    fun getByProductNumber(productNumber: Long): Product

    fun findAll(): List<Product>

    fun findByCategory(category: ProductCategory): List<Product>
}
