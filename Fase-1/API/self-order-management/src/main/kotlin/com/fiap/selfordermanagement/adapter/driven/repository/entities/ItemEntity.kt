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
    @Column(name = "item_caetgory")
    val category: String,
    @ManyToMany
    @JoinTable(
        name = "sub_item",
        joinColumns = [JoinColumn(name = "sub_item_item_id_sub")],
        inverseJoinColumns = [JoinColumn(name = "sub_item_item_id_parent")]
    )
    val subItems: List<ItemEntity>,
)