package com.fiap.selfordermanagement.application.adapter.repository

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory

interface ProductRepository {
    fun findAll(): List<Product>

    fun findByProductNumber(productNumber: Long): Product?

    fun findByCategory(category: ProductCategory): List<Product>

    fun searchByName(name: String): List<Product>

    fun create(product: Product): Product

    fun update(product: Product): Product

    fun delete(productNumber: Long): Product

    fun deleteAll()
}
