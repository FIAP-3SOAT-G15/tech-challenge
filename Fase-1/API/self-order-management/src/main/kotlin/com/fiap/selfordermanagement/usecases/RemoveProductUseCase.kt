package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Product

interface RemoveProductUseCase {
    fun delete(productNumber: Long): Product
}
