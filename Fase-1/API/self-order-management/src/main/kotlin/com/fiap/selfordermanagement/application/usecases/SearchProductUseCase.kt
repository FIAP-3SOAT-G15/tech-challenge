package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Product

interface SearchProductUseCase {
    fun searchByName(productName: String): List<Product>
}
