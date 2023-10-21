package com.fiap.selfordermanagement.adapter.driven.repository.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "orders")
class OrdersEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Long,
    @Column(name = "order_total")
    val total: BigDecimal,
    @Column(name = "order_status")
    val status: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_client_document")
    val client: ClientEntity? = null,
    @Column(name = "order_client_nick_name")
    val nickName: String? = null,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "order_item",
        joinColumns = [JoinColumn(name = "orders_item_item_name")],
        inverseJoinColumns = [JoinColumn(name = "orders_item_order_id")]
    )
    val items : List<ItemEntity>,
)