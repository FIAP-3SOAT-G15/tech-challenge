package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface LoadStockUseCase {
    fun getByComponentNumber(componentNumber: Long): Stock
}
