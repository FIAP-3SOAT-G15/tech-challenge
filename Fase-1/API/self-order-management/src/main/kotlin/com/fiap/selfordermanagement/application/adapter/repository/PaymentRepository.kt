package com.fiap.selfordermanagement.application.adapter.repository

import com.fiap.selfordermanagement.application.domain.entities.Payment

interface PaymentRepository {
    fun findByOrderNumber(orderNumber: Long): Payment?

    fun findAll(): List<Payment>

    fun create(payment: Payment): Payment

    fun update(payment: Payment): Payment
}
