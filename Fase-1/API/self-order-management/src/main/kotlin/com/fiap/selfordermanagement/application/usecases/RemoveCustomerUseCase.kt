package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface RemoveCustomerUseCase {
    fun remove(document: String): Customer
}
