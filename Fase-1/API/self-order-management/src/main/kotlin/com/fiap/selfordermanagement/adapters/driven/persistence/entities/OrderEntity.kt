package com.fiap.selfordermanagement.adapters.driven.persistence.entities

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "order")
class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    val number: Long,
    @Column(name = "order_date")
    val date: LocalDate,
    @Column(name = "order_customer_nickname")
    val customerNickname: String,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_customer_document")
    val customer: CustomerEntity? = null,
    @Column(name = "order_status")
    val status: String,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "order_item",
        joinColumns = [JoinColumn(name = "order_item_order_number")],
        inverseJoinColumns = [JoinColumn(name = "order_item_product_number")],
    )
    val items: List<ProductEntity>,
    @Column(name = "order_total")
    val total: BigDecimal,
)
