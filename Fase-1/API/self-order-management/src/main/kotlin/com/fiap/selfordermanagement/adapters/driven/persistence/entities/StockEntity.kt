package com.fiap.selfordermanagement.adapters.driven.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "stock")
class StockEntity(
    @Id
    @Column(name = "stock_input_number")
    val inputNumber: Long,
    @Column(name = "stock_quantity")
    val quantity: Long,
) {
    fun withNumber(number: Long): StockEntity {
        return StockEntity(number, quantity = quantity)
    }
}
