package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer

interface UpdateCustomerUseCase {
    fun update(customer: Customer): Customer
}
