package com.fiap.selfordermanagement.adapters.driver.web.request

import io.swagger.v3.oas.annotations.media.Schema

data class QuantityRequest(
    @Schema(title = "Quantidade", example = "1", required = true)
    val quantity: Long,
)
