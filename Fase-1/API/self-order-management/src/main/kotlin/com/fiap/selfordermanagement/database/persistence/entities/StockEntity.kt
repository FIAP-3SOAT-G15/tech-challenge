package com.fiap.selfordermanagement.database.persistence.entities

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
    @Column(name = "stock_component_number")
    val componentNumber: Long,
    @Column(name = "stock_quantity")
    val quantity: Long,
    @OneToOne
    @JoinColumn(
        name = "stock_component_number",
        referencedColumnName = "component_number",
        insertable = false,
        updatable = false,
    )
    val component: ComponentEntity?,
)
