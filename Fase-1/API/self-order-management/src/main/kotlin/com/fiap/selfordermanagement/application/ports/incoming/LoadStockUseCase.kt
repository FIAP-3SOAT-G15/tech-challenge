package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface LoadStockUseCase {
    fun getByComponentNumber(componentNumber: Long): Stock
}
