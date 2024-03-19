package com.fiap.selfordermanagement.driver.web.request

import com.fiap.selfordermanagement.domain.entities.OrderItem
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema

data class OrderRequest(
    @ArraySchema(
        schema = Schema(implementation = OrderItemRequest::class, required = true),
        minItems = 1,
    )
    val items: List<OrderItemRequest>,
) {
    fun toOrderItemDomain() = items.map { OrderItem(productNumber = it.productNumber, quantity = it.quantity) }
}
