package com.fiap.selfordermanagement.adapters.driver.web.request

import io.swagger.v3.oas.annotations.media.Schema

data class ProductComposeRequest(
    @Schema(title = "Número de produto", example = "123", required = true)
    val productNumber: Long,
    @Schema(title = "Itens", type = "array", example = "[\"1\", \"2\", \"3\"]", required = true, minLength = 1)
    val items: List<Long>,
)
