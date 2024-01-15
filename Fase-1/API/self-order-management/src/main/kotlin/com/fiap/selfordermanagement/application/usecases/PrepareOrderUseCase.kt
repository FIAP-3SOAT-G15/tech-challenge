package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface PrepareOrderUseCase {
    fun startOrderPreparation(orderNumber: Long): Order

    fun finishOrderPreparation(orderNumber: Long): Order
}
