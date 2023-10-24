package com.fiap.selfordermanagement.core.application.use_cases

import com.fiap.selfordermanagement.core.domain.entities.Order


interface CancelOrderUseCase {

    fun withId(orderId: Long) : Order?

    fun withOrder(order: Order) : Order
}