package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface CreateCustomerUseCase {
    fun create(customer: Customer): Customer
}
