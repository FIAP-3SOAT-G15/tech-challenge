package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface UpdateCustomerUseCase {
    fun update(customer: Customer): Customer
}
