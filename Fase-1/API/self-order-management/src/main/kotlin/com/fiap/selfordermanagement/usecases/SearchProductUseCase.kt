package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Product

interface SearchProductUseCase {
    fun searchByName(productName: String): List<Product>
}
