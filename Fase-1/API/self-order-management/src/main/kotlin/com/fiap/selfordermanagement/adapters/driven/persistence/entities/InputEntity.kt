package com.fiap.selfordermanagement.adapters.driven.persistence.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "inputs")
class InputEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "input_number")
    val number: Long?,
    @Column(name = "input_name")
    val name: String,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(
        name = "input_number",
        referencedColumnName = "stock_input_number",
    )
    val stock: StockEntity?,
) {
    fun withStock(stock: StockEntity?): InputEntity {
        return InputEntity(
            number = number,
            name = name,
            stock = stock,
        )
    }
}
