package com.fiap.selfordermanagement.driver.database.provider

import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.client.MercadoPagoClient
import com.fiap.selfordermanagement.client.MercadoPagoQRCodeOrderRequest
import com.fiap.selfordermanagement.client.MercadoPagoQRCodeOrderRequestItem
import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.entities.PaymentRequest
import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus

class MercadoPagoPaymentProvider(
    private val mercadoPagoClient: MercadoPagoClient,
    private val webhook: String,
) : PaymentProviderGateway {
    override fun createExternalOrder(order: Order): PaymentRequest {
        val response =
            mercadoPagoClient.createMerchantOrder(
                MercadoPagoQRCodeOrderRequest(
                    title = "Order ${order.number}",
                    description = "Ordered at ${order.date} by ${order.customerNickname}",
                    externalReference = order.number.toString(),
                    notificationUrl = webhook,
                    totalAmount = order.total,
                    items =
                        order.items.map { product ->
                            MercadoPagoQRCodeOrderRequestItem(
                                title = product.name,
                                unitPrice = product.price,
                                quantity = 1, // TODO: fix to use order lines with persisted quantities per product
                                unitMeasure = MercadoPagoMeasureUnit.UNIT.measureUnit,
                                totalAmount = product.price,
                            )
                        },
                ),
            )

        return PaymentRequest(
            externalOrderId = response.inStoreOrderId,
            paymentInfo = response.qrData,
        )
    }

    override fun checkExternalOrderStatus(externalOrderId: String): PaymentStatus {
        val response = mercadoPagoClient.fetchMerchantOrder(externalOrderId)

        return when (response.orderStatus) {
            "paid" -> {
                PaymentStatus.CONFIRMED
            }
            "expired" -> {
                PaymentStatus.EXPIRED
            }
            "payment_in_process", "payment_required" -> {
                PaymentStatus.PENDING
            }
            else -> {
                PaymentStatus.FAILED
            }
        }
    }

    enum class MercadoPagoMeasureUnit(val measureUnit: String) {
        UNIT("unit"),
    }
}
