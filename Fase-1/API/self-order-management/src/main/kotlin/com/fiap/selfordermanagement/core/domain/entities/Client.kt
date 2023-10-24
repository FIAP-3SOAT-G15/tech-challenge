package com.fiap.selfordermanagement.core.domain.entities

data class Client(
    val document: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
) {
    fun update(newClient: Client): Client {
        return this.copy(
            name = newClient.name,
            email = newClient.email,
            phone = newClient.phone,
            address = newClient.address,
        )
    }
}
