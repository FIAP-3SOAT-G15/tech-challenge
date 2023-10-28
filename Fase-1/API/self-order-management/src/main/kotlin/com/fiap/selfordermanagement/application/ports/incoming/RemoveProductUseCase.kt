package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Product

interface RemoveProductUseCase {
    fun delete(productNumber: Long): Product
}
