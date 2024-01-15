package com.fiap.selfordermanagement.application.adapter.repository

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface CustomerRepository {
    fun findAll(): List<Customer>

    fun findByDocument(document: String): Customer?

    fun searchByName(name: String): List<Customer>

    fun create(customer: Customer): Customer

    fun update(customer: Customer): Customer

    fun deleteByDocument(document: String): Customer

    fun deleteAll()
}
