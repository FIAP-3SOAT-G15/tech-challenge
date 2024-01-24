package com.fiap.selfordermanagement.adapter.gateway

import com.fiap.selfordermanagement.domain.entities.Payment

interface PaymentGateway {
    fun findByOrderNumber(orderNumber: Long): Payment?

    fun findAll(): List<Payment>

    fun create(payment: Payment): Payment

    fun update(payment: Payment): Payment
}
