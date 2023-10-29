package com.fiap.selfordermanagement.application.domain.entities

data class Stock(
    val inputNumber: Long? = null,
    val quantity: Long,
) {
    fun hasSufficientInventory(quantity: Long) = quantity >= this.quantity
}
