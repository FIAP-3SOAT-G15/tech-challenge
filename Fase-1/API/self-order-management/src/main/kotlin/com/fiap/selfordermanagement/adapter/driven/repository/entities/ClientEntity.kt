package com.fiap.selfordermanagement.adapter.driven.repository.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "client")
class ClientEntity(
    @Id
    @Column(name = "client_document")
    val document: String,
    @Column(name = "client_name")
    val name: String,
    @Column(name = "client_email")
    val email: String,
    @Column(name = "client_phone")
    val phone: String,
    @Column(name = "client_address")
    val address: String,
)
