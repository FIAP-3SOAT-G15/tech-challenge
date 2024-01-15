package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface GetCustomersUseCase {
    fun getCustomers(): List<Customer>
}
