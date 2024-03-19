package com.fiap.selfordermanagement.driver.web.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fiap.selfordermanagement.domain.entities.Customer
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class CustomerRequest(
    @Schema(title = "Documento", example = "444.555.666-77", required = false)
    val document: String?,
    @Schema(title = "Nome do cliente", example = "Fulano de Tal", required = false)
    val name: String?,
    @Schema(title = "E-mail", example = "fulano@email.com", required = false)
    val email: String?,
    @Schema(title = "Telefone", example = "+5511999999999", required = false)
    val phone: String?,
    @Schema(title = "Endereço", example = "Av. Paulista, 1106, 01310-100, São Paulo", required = false)
    val address: String?,
) {
    fun toDomain(): Customer {
        return Customer(
            id = UUID.randomUUID(),
            document = document,
            name = name,
            email = email,
            phone = phone,
            address = address,
        )
    }
}
