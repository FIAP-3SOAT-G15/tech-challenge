package com.fiap.selfordermanagement.application.adapter

import com.fiap.selfordermanagement.application.adapter.mapper.CustomerMapper
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.infra.provider.jpa.CustomerJpaRepository
import org.mapstruct.factory.Mappers

class CustomerRepositoryImpl(private val customerJpaRepository: CustomerJpaRepository) : CustomerRepository {
    private val mapper: CustomerMapper = Mappers.getMapper(CustomerMapper::class.java)

    override fun findByDocument(document: String): Customer? {
        return customerJpaRepository.findById(document)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun getAll(): List<Customer> {
        return customerJpaRepository.findAll().map { mapper.toDomain(it) }
    }

    override fun upsert(customer: Customer): Customer {
        val newCustomer = findByDocument(customer.document)?.let { it.update(customer) } ?: customer
        return newCustomer
            .let(mapper::toEntity)
            .let(block = customerJpaRepository::save)
            .let(mapper::toDomain)
    }
}
