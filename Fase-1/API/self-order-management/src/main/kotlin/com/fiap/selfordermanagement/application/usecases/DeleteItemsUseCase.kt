package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Order

interface DeleteItemsUseCase {
    fun deleteItems(order: Order): Order
}
