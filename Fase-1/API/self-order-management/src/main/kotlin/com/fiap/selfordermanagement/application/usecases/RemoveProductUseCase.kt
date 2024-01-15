package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Product

interface RemoveProductUseCase {
    fun delete(productNumber: Long): Product
}
