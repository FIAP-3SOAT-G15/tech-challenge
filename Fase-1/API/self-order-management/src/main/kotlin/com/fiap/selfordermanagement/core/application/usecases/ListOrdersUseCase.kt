package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Order

interface ListOrdersUseCase {
    fun execute(): List<Order>
}
