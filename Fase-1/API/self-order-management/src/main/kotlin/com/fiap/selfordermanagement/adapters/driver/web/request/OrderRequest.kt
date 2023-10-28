package com.fiap.selfordermanagement.adapters.driver.web.request

data class OrderRequest(
    val customerNickname: String,
    val customerDocument: String?,
    val items: List<OrderItemRequest>,
)
