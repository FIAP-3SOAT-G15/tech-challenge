package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer
import java.util.*

interface LoadCustomerUseCase {
    fun getById(customerId: UUID): Customer

    fun findAll(): List<Customer>

    fun findById(customerId: UUID): Customer?
}
