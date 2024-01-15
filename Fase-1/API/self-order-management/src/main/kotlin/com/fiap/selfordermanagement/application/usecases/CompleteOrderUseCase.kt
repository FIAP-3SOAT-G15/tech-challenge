package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface CompleteOrderUseCase {
    fun completeOrder(orderNumber: Long): Order
}
