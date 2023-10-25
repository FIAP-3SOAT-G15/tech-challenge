package com.fiap.selfordermanagement.adapter.driver.web.api

import com.fiap.selfordermanagement.core.domain.entities.Order
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Orders")
@RequestMapping("/orders")
interface OrdersAPI {
    @GetMapping()
    fun listOrders(): List<Order>
}