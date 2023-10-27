package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Product

interface LoadProductUseCase {
    fun getByProductNumber(productNumber: Long): Product

    fun findAll(): List<Product>
}
