package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Customer

data class CustomerRequest(
    val document: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
) {
    fun toDomain(): Customer {
        return Customer(
            document = document,
            name = name,
            email = email,
            phone = phone,
            address = address,
        )
    }
}
