package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Item
import com.fiap.selfordermanagement.application.domain.entities.Order

interface UpsertItemsUseCase {
    fun upsertItem(order: Order, item: Item): Order
}
