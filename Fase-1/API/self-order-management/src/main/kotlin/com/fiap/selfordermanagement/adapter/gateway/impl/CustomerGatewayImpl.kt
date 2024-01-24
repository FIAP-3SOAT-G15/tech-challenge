package com.fiap.selfordermanagement.adapter.gateway.impl

import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.domain.entities.Customer
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.driver.database.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.mapper.CustomerMapper
import org.mapstruct.factory.Mappers

class CustomerGatewayImpl(
    private val customerJpaRepository: CustomerJpaRepository,
) : CustomerGateway {
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
