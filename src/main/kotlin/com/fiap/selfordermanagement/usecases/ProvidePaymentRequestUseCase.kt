package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.PaymentRequest

interface ProvidePaymentRequestUseCase {
    fun providePaymentRequest(order: Order): PaymentRequest
}
