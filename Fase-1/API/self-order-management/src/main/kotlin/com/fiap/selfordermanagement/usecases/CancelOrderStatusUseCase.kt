package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order

interface CancelOrderStatusUseCase {
    fun cancelOrder(orderNumber: Long): Order
}
