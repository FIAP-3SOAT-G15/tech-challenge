package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.ListOrdersUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class ListOrdersService(private val repository: OrderRepository) : ListOrdersUseCase {
    override fun execute(): List<Order> = repository.findAll()
}
