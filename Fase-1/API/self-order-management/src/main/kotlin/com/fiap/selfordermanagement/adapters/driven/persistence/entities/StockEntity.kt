package com.fiap.selfordermanagement.adapters.driven.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "stock")
class StockEntity(
    @Id
    @Column(name = "stock_product_number")
    val productNumber: Long,
    @Column(name = "stock_quantity")
    val quantity: Long,
    @Column(name = "stock_unit")
    val unit: String,
    @OneToOne
    @JoinColumn(
        name = "stock_product_number",
        referencedColumnName = "product_number",
        insertable = false,
        updatable = false,
    )
    val product: ProductEntity,
)
