package com.fiap.selfordermanagement.application.controller

import com.fiap.selfordermanagement.application.api.CustomersAPI
import com.fiap.selfordermanagement.application.domain.entities.Customer
import com.fiap.selfordermanagement.application.usecases.GetCustomersUseCase
import com.fiap.selfordermanagement.application.usecases.SearchCustomerUseCase
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
