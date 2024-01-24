package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Payment

interface LoadPaymentUseCase {
    fun getByOrderNumber(orderNumber: Long): Payment

    fun findAll(): List<Payment>

    fun findByOrderNumber(orderNumber: Long): Payment?
}
