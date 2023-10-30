package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Customer
import io.swagger.v3.oas.annotations.media.Schema

data class CustomerRequest(
    @Schema(title = "Documento", example = "444.555.666-77", required = true)
    val document: String,
    @Schema(title = "Nome do cliente", example = "Fulano de Tal", required = true)
    val name: String,
    @Schema(title = "E-mail", example = "fulano@email.com", required = true)
    val email: String,
    @Schema(title = "Telefone", example = "+5511999999999", required = true)
    val phone: String,
    @Schema(title = "Endereço", example = "Av. Paulista, 1106, 01310-100, São Paulo", required = true)
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
