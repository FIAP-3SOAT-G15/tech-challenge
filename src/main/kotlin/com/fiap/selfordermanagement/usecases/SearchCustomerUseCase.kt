package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer

interface SearchCustomerUseCase {
    fun searchByName(name: String): List<Customer>
    fun searchByEmail(email: String): Customer?
}
