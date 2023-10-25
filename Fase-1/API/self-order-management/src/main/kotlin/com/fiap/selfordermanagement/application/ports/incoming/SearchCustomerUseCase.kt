package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface SearchCustomerUseCase {
    fun search(document: String): Customer?
}
