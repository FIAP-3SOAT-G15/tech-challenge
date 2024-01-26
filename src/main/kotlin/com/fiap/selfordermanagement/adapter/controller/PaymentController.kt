package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Payment
import com.fiap.selfordermanagement.driver.web.PaymentAPI
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import com.fiap.selfordermanagement.usecases.SyncPaymentUseCase
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val loadPaymentUseCase: LoadPaymentUseCase,
    private val syncPaymentUseCase: SyncPaymentUseCase
) : PaymentAPI {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun findAll(): ResponseEntity<List<Payment>> {
        return ResponseEntity.ok(loadPaymentUseCase.findAll())
    }

    override fun getByOrderNumber(orderNumber: Long): ResponseEntity<Payment> {
        return ResponseEntity.ok(loadPaymentUseCase.getByOrderNumber(orderNumber))
    }

    /**
     * The server response is important to flag the provider for retries
     */
    override fun notify(orderNumber: Long, resourceId: String, topic: String): ResponseEntity<Any> {
        // TODO: verify x-signature header by Mercado Pago
        log.info("Notification received for order ${orderNumber}: type=${topic} externalId=${resourceId}")
        
        when (topic) {
            IPNType.MERCHANT_ORDER.ipnType -> {
                syncPaymentUseCase.syncPayment(orderNumber, resourceId)
                return ResponseEntity.ok().build()
            }
            IPNType.PAYMENT.ipnType -> {
                val payment = loadPaymentUseCase.getByOrderNumber(orderNumber)
                payment.externalOrderGlobalId?.let {
                    syncPaymentUseCase.syncPayment(orderNumber, it)
                    return ResponseEntity.ok().build()
                }
                // returns server error because external order global ID was not previously saved,
                // which does not conform with the usual application flow
                return ResponseEntity.internalServerError().build()
            }
            else -> {
                // returns bad request because application does not accept this kind of IPN types
                return ResponseEntity.badRequest().build()
            }
        }
    }

    enum class IPNType(val ipnType: String) {
        MERCHANT_ORDER("merchant_order"),
        PAYMENT("payment"),
        CHARGEBACK("chargebacks"),
        POINT_INTEGRATION_IPN("point_integration_ipn"),
    }
}
