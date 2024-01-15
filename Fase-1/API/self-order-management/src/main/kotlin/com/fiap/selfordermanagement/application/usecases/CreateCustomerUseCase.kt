package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface CreateCustomerUseCase {
    fun create(customer: Customer): Customer
}
