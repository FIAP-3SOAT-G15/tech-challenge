package com.fiap.selfordermanagement.adapter.gateway

import com.fiap.selfordermanagement.domain.entities.Customer

interface CustomerGateway {
    fun findAll(): List<Customer>

    fun findByDocument(document: String): Customer?

    fun searchByName(name: String): List<Customer>

    fun create(customer: Customer): Customer

    fun update(customer: Customer): Customer

    fun deleteByDocument(document: String): Customer

    fun deleteAll()
}
