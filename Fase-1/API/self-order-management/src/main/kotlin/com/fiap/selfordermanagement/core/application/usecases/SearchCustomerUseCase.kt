package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Customer

interface SearchCustomerUseCase {
    fun execute(document: String): Customer?
}
