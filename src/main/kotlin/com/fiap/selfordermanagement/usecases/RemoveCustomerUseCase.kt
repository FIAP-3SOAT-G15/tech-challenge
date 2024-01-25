package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer

interface RemoveCustomerUseCase {
    fun remove(document: String): Customer
}
