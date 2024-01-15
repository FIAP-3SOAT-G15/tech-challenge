package com.fiap.selfordermanagement.application.adapter.services

import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.usecases.GetCustomersUseCase
import com.fiap.selfordermanagement.application.usecases.SearchCustomerUseCase
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository

class CustomerService(
    private val repository: CustomerRepository,
) : GetCustomersUseCase, SearchCustomerUseCase {
    override fun getCustomers(): List<Customer> {
        return repository.getAll()
    }

    override fun search(document: String): Customer? {
        return repository.findByDocument(document)
    }
}
