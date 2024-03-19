package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.OrderItem
import java.util.*

interface PlaceOrderUseCase {
    fun create(
        customerId: UUID?,
        items: List<OrderItem>,
    ): Order
}
