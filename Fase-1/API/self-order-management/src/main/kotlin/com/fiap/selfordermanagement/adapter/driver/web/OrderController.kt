package com.fiap.selfordermanagement.adapter.driver.web

import com.fiap.selfordermanagement.adapter.driver.web.api.OrderApi
import com.fiap.selfordermanagement.core.application.usecases.ListOrdersUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order

class OrderController(private val listOrdersUseCase: ListOrdersUseCase) : OrderApi {
    override fun listOrders(): List<Order> {
        return listOrdersUseCase.execute()
    }
}
