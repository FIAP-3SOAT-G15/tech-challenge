package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Stock

interface LoadStockUseCase {
    fun getByComponentNumber(componentNumber: Long): Stock
}
