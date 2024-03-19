package com.fiap.selfordermanagement.adapter.gateway.impl

import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.domain.entities.Customer
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.driver.database.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.mapper.CustomerMapper
import org.mapstruct.factory.Mappers
import java.util.*

class CustomerGatewayImpl(
    private val customerJpaRepository: CustomerJpaRepository,
) : CustomerGateway {
    private val mapper: CustomerMapper = Mappers.getMapper(CustomerMapper::class.java)

    override fun findAll(): List<Customer> {
        return customerJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun findById(customerId: UUID): Customer? {
        return customerJpaRepository.findById(customerId.toString())
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun searchByName(name: String): List<Customer> {
        return customerJpaRepository.findByNameContainingIgnoreCase(name)
            .map(mapper::toDomain)
    }

    override fun searchByEmail(email: String): Customer? {
        return customerJpaRepository.findByEmail(email)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun create(customer: Customer): Customer {
        customer.email
            ?.let { searchByEmail(it) }
            ?.let {
                throw SelfOrderManagementException(
                    errorType = ErrorType.CUSTOMER_ALREADY_EXISTS,
                    message = "Customer with email [${customer.email}] already exists",
                )
            }

        customer.document
            ?.let { searchByDocument(it) }
            ?.let {
                throw SelfOrderManagementException(
                    errorType = ErrorType.CUSTOMER_ALREADY_EXISTS,
                    message = "Customer with document [${customer.document}] already exists",
                )
            }

        return persist(customer)
    }

    override fun searchByDocument(document: String): Customer? {
        return customerJpaRepository.findByDocument(document)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun update(customer: Customer): Customer {
        val newItem =
            findById(customer.id)
                ?.update(customer)
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.CUSTOMER_NOT_FOUND,
                    message = "Customer [${customer.document}] not found",
                )
        return persist(newItem)
    }

    override fun deleteById(customerId: UUID): Customer {
        return findById(customerId)
            ?.let {
                customerJpaRepository.deleteById(customerId.toString())
                it
            }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.CUSTOMER_NOT_FOUND,
                message = "Customer [$customerId] not found",
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
