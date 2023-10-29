package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Product

interface ProductRepository {
    fun findAll(): List<Product>

    fun findByProductNumber(productNumber: Long): Product?

    fun searchByName(name: String): List<Product>

    fun create(product: Product): Product

    fun update(product: Product): Product

    fun delete(productNumber: Long): Product

    fun deleteAll()
}
