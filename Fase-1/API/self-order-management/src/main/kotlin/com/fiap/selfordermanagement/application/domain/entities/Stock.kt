package com.fiap.selfordermanagement.application.domain.entities

data class Stock(
    val componentNumber: Long,
    val quantity: Long,
) {
    fun update(newStock: Stock): Stock =
        copy(
            quantity = newStock.quantity,
        )

    fun hasSufficientInventory(quantity: Long) = quantity >= this.quantity
}
