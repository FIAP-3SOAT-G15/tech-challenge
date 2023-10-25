package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.CustomersAPI
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.ports.incoming.GetCustomersUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchCustomerUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomersController(
    private val getCustomersUseCase: GetCustomersUseCase,
    private val searchCustomerUseCase: SearchCustomerUseCase,
) : CustomersAPI {
    override fun getAll(): List<Customer> {
        return getCustomersUseCase.getCustomers()
    }

    override fun search(document: String): Customer? {
        return searchCustomerUseCase.search(document)
    }
}
