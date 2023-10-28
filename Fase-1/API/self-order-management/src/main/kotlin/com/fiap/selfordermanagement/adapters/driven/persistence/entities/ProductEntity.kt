package com.fiap.selfordermanagement.adapters.driven.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
class ProductEntity(
    @Id
    @Column(name = "product_number")
    val number: Long,
    @Column(name = "product_name")
    val name: String,
    @Column(name = "product_type")
    val type: String,
    @Column(name = "product_price")
    val price: BigDecimal,
    @Column(name = "product_description")
    val description: String? = null,
    @Column(name = "product_category")
    val category: String,
    @Column(name = "product_min_sub_item")
    val minSub: Int,
    @Column(name = "product_max_sub_item")
    val maxSub: Int,
    @ManyToMany
    @JoinTable(
        name = "product_sub_item",
        joinColumns = [JoinColumn(name = "product_sub_item_product_id_sub")],
        inverseJoinColumns = [JoinColumn(name = "product_sub_item_product_id_parent")],
    )
    val subItem: List<ProductEntity>,
)
