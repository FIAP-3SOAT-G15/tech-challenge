package com.fiap.selfordermanagement.core.domain.entities

data class Customer(
    val document: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
) {
    fun update(newCustomer: Customer): Customer {
        return this.copy(
            name = newCustomer.name,
            email = newCustomer.email,
            phone = newCustomer.phone,
            address = newCustomer.address,
        )
    }
}
