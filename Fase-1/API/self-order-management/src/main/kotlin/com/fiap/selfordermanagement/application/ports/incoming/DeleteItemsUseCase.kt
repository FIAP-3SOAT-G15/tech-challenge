package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order

interface DeleteItemsUseCase {
    fun deleteItems(order: Order): Order
}
