package com.fiap.selfordermanagement.core.application.use_cases

import com.fiap.selfordermanagement.core.domain.entities.Order

interface DeleteItemsUseCase {
    fun execute(order: Order): Order
}