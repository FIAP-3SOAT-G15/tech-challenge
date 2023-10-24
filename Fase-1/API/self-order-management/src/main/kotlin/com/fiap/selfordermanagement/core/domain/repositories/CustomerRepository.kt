package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Customer

interface CustomerRepository {
    fun findByDocument(document: String): Customer?

    fun getAll(): List<Customer>

    fun upsert(customer: Customer): Customer
}
