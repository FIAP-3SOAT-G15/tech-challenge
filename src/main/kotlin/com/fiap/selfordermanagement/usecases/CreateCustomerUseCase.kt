package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer

interface CreateCustomerUseCase {
    fun create(customer: Customer): Customer
}
