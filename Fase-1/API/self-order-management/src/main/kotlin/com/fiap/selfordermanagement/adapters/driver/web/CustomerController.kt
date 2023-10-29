package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.CustomersAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.CustomerRequest
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.ports.incoming.CreateCustomerUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadCustomerUseCase
import com.fiap.selfordermanagement.application.ports.incoming.RemoveCustomerUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchCustomerUseCase
import com.fiap.selfordermanagement.application.ports.incoming.UpdateCustomerUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val getCustomersUseCase: LoadCustomerUseCase,
    private val searchCustomerUseCase: SearchCustomerUseCase,
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val removeCustomerUseCase: RemoveCustomerUseCase,
) : CustomersAPI {
    override fun getByDocument(document: String): ResponseEntity<Customer?> {
        return ResponseEntity.ok(getCustomersUseCase.getByDocument(document))
    }

    override fun findAll(): ResponseEntity<List<Customer>> {
        return ResponseEntity.ok(getCustomersUseCase.findAll())
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
