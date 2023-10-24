package com.fiap.selfordermanagement.core.application.use_cases.impl

import com.fiap.selfordermanagement.core.application.use_cases.ListOrdersUseCase
import com.fiap.selfordermanagement.core.domain.entities.Order
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository

class ListOrdersService(private val repository: OrderRepository): ListOrdersUseCase {
    override fun execute(): List<Order> = repository.findAll()
}