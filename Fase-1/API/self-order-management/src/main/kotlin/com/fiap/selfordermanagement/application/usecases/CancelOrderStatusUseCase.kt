package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface CancelOrderStatusUseCase {
    fun cancelOrder(orderNumber: Long): Order
}
