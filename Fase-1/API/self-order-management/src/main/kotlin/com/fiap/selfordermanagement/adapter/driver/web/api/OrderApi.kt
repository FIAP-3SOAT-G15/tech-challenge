package com.fiap.selfordermanagement.adapter.driver.web.api

import com.fiap.selfordermanagement.core.domain.entities.Order
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/self_orders/orders")
interface OrderApi {
    @GetMapping()
    fun listOrders(): List<Order>
}
