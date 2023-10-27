package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Product

interface SearchProductUseCase {
    fun searchByName(productName: String): List<Product>
}
