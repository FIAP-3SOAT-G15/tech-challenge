package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Customer

interface GetAllCustomersUseCase {
    fun execute(): List<Customer>
}
