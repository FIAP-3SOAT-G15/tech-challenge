package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer

interface LoadCustomerUseCase {
    fun getByDocument(document: String): Customer

    fun findAll(): List<Customer>

    fun findByDocument(document: String): Customer?
}
