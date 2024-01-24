package com.fiap.selfordermanagement.driver.web.request

import io.swagger.v3.oas.annotations.media.Schema

data class OrderItemRequest(
    @Schema(title = "Número de produto", example = "123", required = true)
    val productNumber: Long,
    @Schema(title = "Quantidade", example = "1", required = true)
    val quantity: Long,
)
