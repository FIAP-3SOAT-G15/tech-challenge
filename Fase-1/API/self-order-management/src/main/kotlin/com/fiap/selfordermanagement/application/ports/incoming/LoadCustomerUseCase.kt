package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface LoadCustomerUseCase {
    fun getByDocument(document: String): Customer

    fun findAll(): List<Customer>

    fun findByDocument(document: String): Customer?
}
