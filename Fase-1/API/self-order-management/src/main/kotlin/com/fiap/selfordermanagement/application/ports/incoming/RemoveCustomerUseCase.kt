package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface RemoveCustomerUseCase {
    fun remove(document: String): Customer
}
