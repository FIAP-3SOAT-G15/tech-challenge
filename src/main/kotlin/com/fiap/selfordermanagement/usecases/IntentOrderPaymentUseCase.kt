package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.PaymentRequest

interface IntentOrderPaymentUseCase {
    fun intentToPayOrder(orderNumber: Long): PaymentRequest
}
