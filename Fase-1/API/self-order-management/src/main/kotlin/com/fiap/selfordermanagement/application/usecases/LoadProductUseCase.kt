package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory

interface LoadProductUseCase {
    fun getByProductNumber(productNumber: Long): Product

    fun findAll(): List<Product>

    fun findByCategory(category: ProductCategory): List<Product>
}
