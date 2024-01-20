package com.fiap.selfordermanagement.application.adapter.services

import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.usecases.*

class CustomerService(
    private val repository: CustomerRepository,
) : LoadCustomerUseCase,
    SearchCustomerUseCase,
    CreateCustomerUseCase,
    UpdateCustomerUseCase,
    RemoveCustomerUseCase {
    override fun getByDocument(document: String): Customer {
        return repository.findByDocument(document)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.CUSTOMER_NOT_FOUND,
                message = "Customer [$document] not found",
            )
    }

    override fun findAll(): List<Customer> {
        return repository.findAll()
    }

    override fun findByDocument(document: String): Customer? {
        return repository.findByDocument(document)
    }

    override fun searchByName(name: String): List<Customer> {
        return repository.searchByName(name.trim())
    }

    override fun create(customer: Customer): Customer {
        return repository.create(customer)
    }

    override fun update(customer: Customer): Customer {
        return repository.update(customer)
    }

    override fun remove(document: String): Customer {
        return repository.deleteByDocument(document)
    }
}
