package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.Payment
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import java.math.BigDecimal

interface ProvidePaymentRequestUseCase {
    fun provideNew(
        orderNumber: Long,
        amount: BigDecimal,
    ): PaymentRequest

    fun provideWith(
        order: Order,
        payment: Payment,
    ): PaymentRequest
}
