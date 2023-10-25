package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface CustomerRepository {
    fun findByDocument(document: String): Customer?

    fun getAll(): List<Customer>

    fun upsert(customer: Customer): Customer
}
