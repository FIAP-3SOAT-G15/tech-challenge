package com.fiap.selfordermanagement.client

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@Component
class MercadoPagoClient(
    private val restTemplate: RestTemplate,
    @Value("\${mercadopago.api.url}") private val apiUrl: String,
    @Value("\${mercadopago.api.token}") private val apiToken: String,
    @Value("\${mercadopago.api.userId}") private val userId: String,
    @Value("\${mercadopago.integration.posId}") private val posId: String,
) {

    fun submitMerchantOrder(request: MercadoPagoQRCodeOrderRequest): MercadoPagoQRCodeOrderResponse {
        val headers = createHeaders()
        val entity = HttpEntity<Any>(request, headers)
        val url = "$apiUrl/instore/orders/qr/seller/collectors/$userId/pos/$posId/qrs"
        val response = executeRequest(url, HttpMethod.POST, entity, MercadoPagoQRCodeOrderResponse::class.java)
        return response.body ?: throw IllegalStateException("No response from Mercado Pago")
    }

    fun fetchMerchantOrder(externalOrderGlobalId: String): MercadoPagoMerchantOrderResponse {
        val headers = createHeaders()
        val entity = HttpEntity<Any>(headers)
        val url = "$apiUrl/merchant_orders/$externalOrderGlobalId"
        val response = executeRequest(url, HttpMethod.GET, entity, MercadoPagoMerchantOrderResponse::class.java)
        return response.body ?: throw IllegalStateException("No response from Mercado Pago")
    }

    private fun createHeaders(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(apiToken)
        return headers
    }

    private fun <T> executeRequest(
        url: String,
        method: HttpMethod,
        entity: HttpEntity<Any>,
        responseType: Class<T>,
    ): ResponseEntity<T> {
        return try {
            restTemplate.exchange(url, method, entity, responseType)
        } catch (e: Exception) {
            throw IllegalStateException("Error while communicating with Mercado Pago API: ${e.message}", e)
        }
    }
}

data class MercadoPagoQRCodeOrderRequest(
    val title: String,
    val description: String,
    @JsonProperty(value = "external_reference") val externalReference: String,
    @JsonProperty(value = "notification_url") val notificationUrl: String,
    @JsonProperty(value = "total_amount") val totalAmount: BigDecimal,
    val items: List<MercadoPagoQRCodeOrderRequestItem>,
)

data class MercadoPagoQRCodeOrderRequestItem(
    val title: String,
    @JsonProperty(value = "unit_price") val unitPrice: BigDecimal,
    val quantity: Long,
    @JsonProperty(value = "unit_measure") val unitMeasure: String,
    @JsonProperty(value = "total_amount") val totalAmount: BigDecimal,
)

data class MercadoPagoQRCodeOrderResponse(
    @JsonProperty(value = "qr_data") val qrData: String,
    @JsonProperty(value = "in_store_order_id") val inStoreOrderId: String,
)

data class MercadoPagoMerchantOrderResponse(
    val id: String,
    @JsonProperty(value = "external_reference") val externalReference: String,
    @JsonProperty(value = "order_status") val orderStatus: String,
)
