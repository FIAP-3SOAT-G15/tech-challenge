package com.fiap.selfordermanagement.application.controller

import com.fiap.selfordermanagement.application.api.OrdersAPI
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.usecases.GetOrdersUseCase

class OrderController(private val getOrdersUseCase: GetOrdersUseCase) : OrdersAPI {
    override fun listOrders(): List<Order> {
        return getOrdersUseCase.getOrders()
    }
}
