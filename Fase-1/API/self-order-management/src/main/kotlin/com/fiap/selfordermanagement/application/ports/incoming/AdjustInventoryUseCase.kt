package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface AdjustInventoryUseCase {
    fun increment(
        productNumber: Long,
        quantity: Long,
    ): Stock

    fun decrement(
        productNumber: Long,
        quantity: Long,
    ): Stock
}
