package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.GetAllCustomersUseCase
import com.fiap.selfordermanagement.core.domain.entities.Customer
import com.fiap.selfordermanagement.core.domain.repositories.CustomerRepository

class GetAllCustomersService(private val repository: CustomerRepository) : GetAllCustomersUseCase {
    override fun execute(): List<Customer> {
        return repository.getAll()
    }
}
