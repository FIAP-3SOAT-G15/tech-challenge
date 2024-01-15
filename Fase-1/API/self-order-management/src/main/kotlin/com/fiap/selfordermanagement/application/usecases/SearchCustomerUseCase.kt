package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Customer

interface SearchCustomerUseCase {
    fun search(document: String): Customer?
}
