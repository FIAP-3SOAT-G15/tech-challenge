package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest

interface IntentOrderPaymentUseCase {
    fun intentToPayOrder(orderNumber: Long): PaymentRequest
}
