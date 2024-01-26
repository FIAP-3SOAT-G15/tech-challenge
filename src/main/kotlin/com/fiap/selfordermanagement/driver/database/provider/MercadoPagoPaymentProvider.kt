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
    private val webhookBaseUrl: String,
) : PaymentProviderGateway {
    
    override fun createExternalOrder(order: Order): PaymentRequest {
        // source_news=ipn indicates application will receive only Instant Payment Notifications (IPNs), not webhooks
        val notificationUrl = "${webhookBaseUrl}/payments/notifications/${order.number}?source_news=ipn"

        val response =
            mercadoPagoClient.submitMerchantOrder(
                MercadoPagoQRCodeOrderRequest(
                    title = "Order ${order.number}",
                    description = "Ordered at ${order.date} by ${order.customerNickname}",
                    externalReference = order.number.toString(),
                    notificationUrl = notificationUrl,
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
            externalOrderGlobalId = null,
            paymentInfo = response.qrData,
        )
    }

    override fun checkExternalOrderStatus(externalOrderGlobalId: String): PaymentStatus {
        val response = mercadoPagoClient.fetchMerchantOrder(externalOrderGlobalId)

        return when (response.orderStatus) {
            MercadoPagoOrderStatus.PAID.orderStatus -> {
                PaymentStatus.CONFIRMED
            }
            MercadoPagoOrderStatus.EXPIRED.orderStatus -> {
                PaymentStatus.EXPIRED
            }
            MercadoPagoOrderStatus.PAYMENT_IN_PROCESS.orderStatus,
            MercadoPagoOrderStatus.PAYMENT_REQUIRED.orderStatus -> {
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

    /**
     * Not exhaustive list of order statuses.
     */
    enum class MercadoPagoOrderStatus(val orderStatus: String) {
        PAID("paid"),
        EXPIRED("expired"),
        PAYMENT_IN_PROCESS("payment_in_process"),
        PAYMENT_REQUIRED("payment_required"),
    }
}
