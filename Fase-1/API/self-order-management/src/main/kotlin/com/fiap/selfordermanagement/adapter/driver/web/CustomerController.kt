package com.fiap.selfordermanagement.adapter.driver.web

import com.fiap.selfordermanagement.adapter.driver.web.api.CustomerApi
import com.fiap.selfordermanagement.core.application.usecases.GetAllCustomersUseCase
import com.fiap.selfordermanagement.core.application.usecases.SearchCustomerUseCase
import com.fiap.selfordermanagement.core.domain.entities.Customer
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerController(
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val searchCustomerUseCase: SearchCustomerUseCase,
) : CustomerApi {
    override fun getAll(): List<Customer> {
        return getAllCustomersUseCase.execute()
    }

    override fun search(document: String): Customer? {
        return searchCustomerUseCase.execute(document)
    }
}
