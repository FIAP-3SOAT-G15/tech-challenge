package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Stock

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
