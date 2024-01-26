package com.fiap.selfordermanagement.driver.web.request

import io.swagger.v3.oas.annotations.media.Schema

data class ProductComposeRequest(
    @Schema(title = "Número de produto", example = "1", required = true)
    val productNumber: Long,
    @Schema(
        title = "Números dos produtos subitens",
        type = "array",
        example = "[\"1\", \"2\", \"3\"]",
        required = true,
        minLength = 1,
    )
    val subItemsNumbers: List<Long>,
)
