package com.fiap.selfordermanagement.usecases.services

import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.domain.entities.Customer
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.usecases.*
import java.util.*

class CustomerService(
    private val repository: CustomerGateway,
) : LoadCustomerUseCase,
    SearchCustomerUseCase,
    CreateCustomerUseCase,
    UpdateCustomerUseCase,
    RemoveCustomerUseCase {
    override fun getById(customerId: UUID): Customer {
        return repository.findById(customerId)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.CUSTOMER_NOT_FOUND,
                message = "Customer [$customerId] not found",
            )
    }

    override fun findAll(): List<Customer> {
        return repository.findAll()
    }

    override fun findById(customerId: UUID): Customer? {
        return repository.findById(customerId)
    }

    override fun searchByName(name: String): List<Customer> {
        return repository.searchByName(name.trim())
    }

    override fun searchByEmail(email: String): Customer? {
        return repository.searchByEmail(email.trim())
    }

    override fun create(customer: Customer): Customer {
        return repository.create(customer.copy(id = UUID.randomUUID()))
    }

    override fun update(customer: Customer): Customer {
        return repository.update(customer)
    }

    override fun remove(customerId: UUID): Customer {
        return repository.deleteById(customerId)
    }
}
