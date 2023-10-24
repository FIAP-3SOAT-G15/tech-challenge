package com.fiap.selfordermanagement.adapter.driven.repository.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "item")
class ItemEntity(
    @Id
    @Column(name = "item_name")
    val name: String,
    @Column(name = "item_type")
    val type: String,
    @Column(name = "item_cost_price")
    val price: BigDecimal,
    @Column(name = "item_description")
    val description: String?=null,
    @Column(name = "item_category")
    val category: String,
    @Column(name = "item_min_sub_item")
    val minSub: Int,
    @Column(name = "item_max_sub_item")
    val maxSub: Int,
    @ManyToMany
    @JoinTable(
        name = "sub_item",
        joinColumns = [JoinColumn(name = "sub_item_item_id_sub")],
        inverseJoinColumns = [JoinColumn(name = "sub_item_item_id_parent")]
    )
    val subItem: List<ItemEntity>,
)