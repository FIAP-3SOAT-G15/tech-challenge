package com.fiap.selfordermanagement.adapters.driver.web.request

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

data class OrderRequest(
    @Schema(title = "Apelido do cliente", example = "Fulano", required = true)
    val customerNickname: String,
    @Schema(title = "Documento do cliente", example = "444.555.666-77", required = false)
    val customerDocument: String?,
    @ArraySchema(
        schema = Schema(implementation = OrderItemRequest::class, required = true),
        minItems = 1,
    )
    val items: List<OrderItemRequest>,
)
