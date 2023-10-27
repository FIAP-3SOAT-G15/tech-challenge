package com.fiap.selfordermanagement.application.domain.entities

data class Stock(
    val productNumber: Long?,
    val quantity: Long,
    val unit: String,
) {
    fun hasSufficientInventory(quantity: Long) = quantity >= this.quantity
}
