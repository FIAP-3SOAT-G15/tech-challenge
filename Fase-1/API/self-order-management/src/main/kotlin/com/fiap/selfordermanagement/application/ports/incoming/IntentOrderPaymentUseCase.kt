package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.PaymentRequest

interface IntentOrderPaymentUseCase {
    fun intentToPayOrder(orderNumber: Long): PaymentRequest
}
