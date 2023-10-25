package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.ports.incoming.GetCustomersUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchCustomerUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository

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
