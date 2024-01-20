package com.fiap.selfordermanagement.database.persistence

import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.database.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.database.persistence.mapper.CustomerMapper
import org.mapstruct.factory.Mappers

class CustomerRepositoryImpl(
    private val customerJpaRepository: CustomerJpaRepository,
) : CustomerRepository {
    private val mapper: CustomerMapper = Mappers.getMapper(CustomerMapper::class.java)

    override fun findAll(): List<Customer> {
        return customerJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun findByDocument(document: String): Customer? {
        return customerJpaRepository.findById(document)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun searchByName(name: String): List<Customer> {
        return customerJpaRepository.findByNameContainingIgnoreCase(name)
            .map(mapper::toDomain)
    }

    override fun create(customer: Customer): Customer {
        findByDocument(customer.document)
            ?.let {
                throw SelfOrderManagementException(
                    errorType = ErrorType.CUSTOMER_ALREADY_EXISTS,
                    message = "Customer [${customer.document}] already exists",
                )
            }
        return persist(customer)
    }

    override fun update(customer: Customer): Customer {
        val newItem =
            findByDocument(customer.document)
                ?.update(customer)
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.CUSTOMER_NOT_FOUND,
                    message = "Customer [${customer.document}] not found",
                )
        return persist(newItem)
    }

    override fun deleteByDocument(document: String): Customer {
        return findByDocument(document)
            ?.let {
                customerJpaRepository.deleteById(document)
                it
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.CUSTOMER_NOT_FOUND,
                message = "Customer [$document] not found",
            )
    }

    override fun deleteAll() {
        customerJpaRepository.deleteAll()
    }

    private fun persist(customer: Customer): Customer =
        customer
            .let(mapper::toEntity)
            .let(customerJpaRepository::save)
            .let(mapper::toDomain)
}
