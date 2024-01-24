package com.fiap.selfordermanagement.driver.database.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val number: Long?,
    @Column(name = "product_name")
    val name: String,
    @Column(name = "product_category")
    val category: String,
    @Column(name = "product_price")
    val price: BigDecimal,
    @Column(name = "product_description")
    val description: String? = null,
    @ManyToMany
    @JoinTable(
        name = "product_component",
        joinColumns = [JoinColumn(name = "product_component_product_number")],
        inverseJoinColumns = [JoinColumn(name = "product_component_component_number")],
    )
    val components: List<ComponentEntity>,
    @ManyToMany
    @JoinTable(
        name = "product_sub_item",
        joinColumns = [JoinColumn(name = "product_sub_item_product_id_sub")],
        inverseJoinColumns = [JoinColumn(name = "product_sub_item_product_id_parent")],
    )
    val subItems: List<ProductEntity>,
    @Column(name = "product_min_sub_item")
    val minSub: Int,
    @Column(name = "product_max_sub_item")
    val maxSub: Int,
)
