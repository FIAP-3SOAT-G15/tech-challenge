package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Payment

interface LoadPaymentUseCase {
    fun getByOrderNumber(orderNumber: Long): Payment

    fun findAll(): List<Payment>

    fun findByOrderNumber(orderNumber: Long): Payment?
}
