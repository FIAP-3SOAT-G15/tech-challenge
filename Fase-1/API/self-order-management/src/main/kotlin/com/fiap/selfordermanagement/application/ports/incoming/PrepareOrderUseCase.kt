package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order

interface PrepareOrderUseCase {
    fun startOrderPreparation(orderNumber: Long): Order

    fun finishOrderPreparation(orderNumber: Long): Order
}
