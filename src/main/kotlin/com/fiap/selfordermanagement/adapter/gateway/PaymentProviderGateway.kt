package com.fiap.selfordermanagement.adapter.gateway

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus

interface PaymentProviderGateway {
    fun createExternalOrder(order: Order): PaymentRequest

    fun checkExternalOrderStatus(externalOrderId: String): PaymentStatus
}
