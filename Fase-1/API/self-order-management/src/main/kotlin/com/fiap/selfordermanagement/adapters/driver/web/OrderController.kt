package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.OrdersAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.OrderRequest
import com.fiap.selfordermanagement.adapters.driver.web.response.PaymentRequestResponse
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.application.ports.incoming.CancelOrderStatusUseCase
import com.fiap.selfordermanagement.application.ports.incoming.CompleteOrderUseCase
import com.fiap.selfordermanagement.application.ports.incoming.ConfirmOrderUseCase
import com.fiap.selfordermanagement.application.ports.incoming.IntentOrderPaymentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadOrderUseCase
import com.fiap.selfordermanagement.application.ports.incoming.PlaceOrderUseCase
import com.fiap.selfordermanagement.application.ports.incoming.PrepareOrderUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val getOrdersUseCase: LoadOrderUseCase,
    private val createOrderUseCase: PlaceOrderUseCase,
    private val intentOrderPaymentUseCase: IntentOrderPaymentUseCase,
    private val confirmOrderUseCase: ConfirmOrderUseCase,
    private val prepareOrderUseCase: PrepareOrderUseCase,
    private val completeOrderUseCase: CompleteOrderUseCase,
    private val cancelOrderStatusUseCase: CancelOrderStatusUseCase,
) : OrdersAPI {
    override fun getByOrderNumber(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(getOrdersUseCase.getByOrderNumber(orderNumber))
    }

    override fun findAll(): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(getOrdersUseCase.findAll())
    }

    override fun getByStatus(status: String): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(getOrdersUseCase.findByStatus(OrderStatus.fromString(status)))
    }

    override fun getByStatusAndCustomer(
        status: String,
        customerNickname: String?,
        customerDocument: String?,
    ): ResponseEntity<List<Order>> {
        val orderStatus = OrderStatus.fromString(status)
        val orders =
            when {
                customerNickname != null -> getOrdersUseCase.findByCustomerNicknameAndStatus(customerNickname, orderStatus)
                customerDocument != null -> getOrdersUseCase.findByCustomerDocumentAndStatus(customerDocument, orderStatus)
                else -> getOrdersUseCase.findByStatus(orderStatus)
            }
        return ResponseEntity.ok(orders)
    }

    override fun getByCustomer(
        customerNickname: String?,
        customerDocument: String?,
    ): ResponseEntity<List<Order>> {
        val orders =
            when {
                customerNickname != null -> getOrdersUseCase.findByCustomerNickname(customerNickname)
                customerDocument != null -> getOrdersUseCase.findByCustomerDocument(customerDocument)
                else -> getOrdersUseCase.findAll()
            }
        return ResponseEntity.ok(orders)
    }

    override fun create(orderRequest: OrderRequest): ResponseEntity<Order> {
        return ResponseEntity.ok(
            createOrderUseCase.create(orderRequest.customerNickname, orderRequest.customerDocument, orderRequest.items),
        )
    }

    override fun pay(orderNumber: Long): ResponseEntity<PaymentRequestResponse> {
        val paymentRequest = intentOrderPaymentUseCase.intentToPayOrder(orderNumber)
        return ResponseEntity.ok(PaymentRequestResponse(paymentRequest.qrCode))
    }

    override fun confirm(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(confirmOrderUseCase.confirmOrder(orderNumber))
    }

    override fun startPreparation(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(prepareOrderUseCase.startOrderPreparation(orderNumber))
    }

    override fun finishPreparation(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(prepareOrderUseCase.finishOrderPreparation(orderNumber))
    }

    override fun complete(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(completeOrderUseCase.completeOrder(orderNumber))
    }

    override fun cancel(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(cancelOrderStatusUseCase.cancelOrder(orderNumber))
    }
}
