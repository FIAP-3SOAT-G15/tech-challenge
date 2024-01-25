package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Customer
import com.fiap.selfordermanagement.driver.web.CustomersAPI
import com.fiap.selfordermanagement.driver.web.request.CustomerRequest
import com.fiap.selfordermanagement.usecases.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val loadCustomersUseCase: LoadCustomerUseCase,
    private val searchCustomerUseCase: SearchCustomerUseCase,
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
) : CustomersAPI {
    override fun getByDocument(document: String): ResponseEntity<Customer?> {
        return ResponseEntity.ok(loadCustomersUseCase.getByDocument(document))
    }

    override fun findAll(): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(loadCustomersUseCase.findAll())
    }

    override fun searchByName(name: String): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(searchCustomerUseCase.searchByName(name))
    }

    override fun create(customerRequest: CustomerRequest): ResponseEntity<Customer> {
        return ResponseEntity.ok(createCustomerUseCase.create(customerRequest.toDomain()))
    }

    override fun update(
        document: String,
        customerRequest: CustomerRequest,
    ): ResponseEntity<Customer> {
        val identifiedCustomerRequest = customerRequest.copy(document = document)
        return ResponseEntity.ok(updateCustomerUseCase.update(identifiedCustomerRequest.toDomain()))
    }

    override fun remove(document: String): ResponseEntity<Customer> {
        return ResponseEntity.ok(removeCustomerUseCase.remove(document))
    }
}
