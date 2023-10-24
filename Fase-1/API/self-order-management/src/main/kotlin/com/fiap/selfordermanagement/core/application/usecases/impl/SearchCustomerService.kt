package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.SearchCustomerUseCase
import com.fiap.selfordermanagement.core.domain.entities.Customer
import com.fiap.selfordermanagement.core.domain.repositories.CustomerRepository

class SearchCustomerService(private val repository: CustomerRepository) : SearchCustomerUseCase {
    override fun execute(document: String): Customer? {
        return repository.findByDocument(document)
    }
}
