package com.fiap.selfordermanagement.driver.database.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "customer")
class CustomerEntity(
    @Id
    @Column(name = "customer_id")
    val id: String,
    @Column(name = "customer_document")
    val document: String?,
    @Column(name = "customer_name")
    val name: String?,
    @Column(name = "customer_email")
    val email: String?,
    @Column(name = "customer_phone")
    val phone: String?,
    @Column(name = "customer_address")
    val address: String?,
)
