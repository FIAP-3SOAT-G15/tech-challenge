package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Order

interface OrderRepository {
    fun complete(order: Order): Order
}