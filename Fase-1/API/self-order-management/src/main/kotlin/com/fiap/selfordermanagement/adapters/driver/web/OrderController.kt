package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.OrdersAPI
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.ports.incoming.GetOrdersUseCase

class OrderController(private val getOrdersUseCase: GetOrdersUseCase) : OrdersAPI {
    override fun listOrders(): List<Order> {
        return getOrdersUseCase.getOrders()
    }
}
