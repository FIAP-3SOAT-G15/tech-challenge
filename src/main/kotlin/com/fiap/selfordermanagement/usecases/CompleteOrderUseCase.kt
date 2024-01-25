package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order

interface CompleteOrderUseCase {
    fun completeOrder(orderNumber: Long): Order
}
