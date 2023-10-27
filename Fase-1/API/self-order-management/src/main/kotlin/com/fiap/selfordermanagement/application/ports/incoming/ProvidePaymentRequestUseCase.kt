package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.entities.Payment
import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest
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
