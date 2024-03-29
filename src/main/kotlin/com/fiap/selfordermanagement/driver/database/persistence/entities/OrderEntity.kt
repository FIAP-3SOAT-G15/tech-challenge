package com.fiap.selfordermanagement.driver.database.persistence.entities

import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_customer_id")
    val customer: CustomerEntity? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    val status: OrderStatus,
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
