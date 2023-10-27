package com.fiap.selfordermanagement.adapters.driver.web.request

data class ProductComposeRequest(
    val productNumber: Long,
    val items: List<Long>,
)
