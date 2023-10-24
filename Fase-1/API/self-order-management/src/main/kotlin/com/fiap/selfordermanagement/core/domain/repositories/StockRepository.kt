package com.fiap.selfordermanagement.core.domain.repositories

interface StockRepository {
    fun increment(
        itemName: String,
        qtde: Long,
    )

    fun decrement(
        itemName: String,
        qtde: Long,
    )

    fun isThereAvailable(
        itemName: String,
        qtde: Long,
    ): Boolean
}
