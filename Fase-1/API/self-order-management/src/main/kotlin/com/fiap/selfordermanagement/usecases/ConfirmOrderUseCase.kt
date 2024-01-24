package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order

interface ConfirmOrderUseCase {
    fun confirmOrder(orderNumber: Long): Order
}
