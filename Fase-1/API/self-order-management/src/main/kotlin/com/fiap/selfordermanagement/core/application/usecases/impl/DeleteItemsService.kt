package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.DeleteItemsUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class DeleteItemsService(private val repository: OrderRepository) : DeleteItemsUseCase {
    override fun execute(order: Order): Order {
        return repository.deleteItems(order)
    }
}
