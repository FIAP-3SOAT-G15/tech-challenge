package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.domain.entities.Stock
import io.swagger.v3.oas.annotations.media.Schema

data class NewInputRequest(
    @Schema(title = "Nome", example = "Pão de hambúrguer", required = true)
    val name: String,
    @Schema(title = "Quantidade", example = "100", required = true)
    val quantity: Long,
) {
    fun toDomain(): Input {
        return Input(name = name, stock = Stock(quantity = quantity))
    }
}
