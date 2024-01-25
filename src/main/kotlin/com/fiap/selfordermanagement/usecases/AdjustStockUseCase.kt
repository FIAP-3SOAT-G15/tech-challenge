package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Stock

interface AdjustStockUseCase {
    fun increment(
        componentNumber: Long,
        quantity: Long,
    ): Stock

    fun decrement(
        componentNumber: Long,
        quantity: Long,
    ): Stock
}
