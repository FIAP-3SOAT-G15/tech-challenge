package com.fiap.selfordermanagement.core.domain.repositories

interface StockRepository {
    fun increment(
        itemName: String,
        quantity: Long,
    )

    fun decrement(
        itemName: String,
        quantity: Long,
    )

    fun isThereAvailable(
        itemName: String,
        quantity: Long,
    ): Boolean
}
