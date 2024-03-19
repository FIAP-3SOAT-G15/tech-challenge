package com.fiap.selfordermanagement.domain.entities

import java.util.*

data class Customer(
    val id: UUID,
    val document: String?,
    val name: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
) {
    fun update(newCustomer: Customer): Customer =
        copy(
            document = newCustomer.document,
            name = newCustomer.name,
            email = newCustomer.email,
            phone = newCustomer.phone,
            address = newCustomer.address,
        )
}
