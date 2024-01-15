package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface GetOrdersUseCase {
    fun getOrders(): List<Order>
}
