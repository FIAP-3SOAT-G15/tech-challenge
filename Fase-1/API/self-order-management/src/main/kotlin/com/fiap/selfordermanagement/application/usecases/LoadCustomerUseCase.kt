package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface LoadCustomerUseCase {
    fun getByDocument(document: String): Customer

    fun findAll(): List<Customer>

    fun findByDocument(document: String): Customer?
}
