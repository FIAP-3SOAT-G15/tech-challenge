package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface ConfirmOrderUseCase {
    fun confirmOrder(orderNumber: Long): Order
}
