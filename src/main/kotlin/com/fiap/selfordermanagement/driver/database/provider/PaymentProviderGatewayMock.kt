package com.fiap.selfordermanagement.driver.database.provider

import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import java.util.*

class PaymentProviderGatewayMock: PaymentProviderGateway {

    override fun createExternalOrder(order: Order): PaymentRequest {
        return PaymentRequest(
            externalOrderId = UUID.randomUUID().toString(),
            externalOrderGlobalId = null,
            paymentInfo = "mocked"
        )
    }

    override fun checkExternalOrderStatus(externalOrderGlobalId: String): PaymentStatus {
        // always confirming
        return PaymentStatus.CONFIRMED
    }
}
