package com.fiap.selfordermanagement.domain.entities

import java.util.*

data class Customer(
    val id: UUID,
    val document: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
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
