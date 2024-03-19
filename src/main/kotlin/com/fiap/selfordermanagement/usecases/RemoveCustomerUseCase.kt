package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Customer
import java.util.*

interface RemoveCustomerUseCase {
    fun remove(customerId: UUID): Customer
}
