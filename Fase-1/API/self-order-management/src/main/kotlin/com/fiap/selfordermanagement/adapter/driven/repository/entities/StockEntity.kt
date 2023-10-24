package com.fiap.selfordermanagement.adapter.driven.repository.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "stock")
class StockEntity(
    @Id
    @Column(name = "stock_item_name")
    val item: String,
    @Column(name = "stock_quantity")
    val quantity: Long,
    @Column(name = "stock_unit")
    val unit: String,
)
