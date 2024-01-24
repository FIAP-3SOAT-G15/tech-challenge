package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order

interface PrepareOrderUseCase {
    fun startOrderPreparation(orderNumber: Long): Order

    fun finishOrderPreparation(orderNumber: Long): Order
}
