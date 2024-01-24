package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.driver.web.OrdersAPI
import com.fiap.selfordermanagement.driver.web.request.OrderRequest
import com.fiap.selfordermanagement.driver.web.response.PaymentRequestResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val loadOrdersUseCase: com.fiap.selfordermanagement.usecases.LoadOrderUseCase,
    private val createOrderUseCase: com.fiap.selfordermanagement.usecases.PlaceOrderUseCase,
    private val intentOrderPaymentUseCase: com.fiap.selfordermanagement.usecases.IntentOrderPaymentUseCase,
    private val confirmOrderUseCase: com.fiap.selfordermanagement.usecases.ConfirmOrderUseCase,
    private val prepareOrderUseCase: com.fiap.selfordermanagement.usecases.PrepareOrderUseCase,
    private val completeOrderUseCase: com.fiap.selfordermanagement.usecases.CompleteOrderUseCase,
    private val cancelOrderStatusUseCase: com.fiap.selfordermanagement.usecases.CancelOrderStatusUseCase,
) : OrdersAPI {
    override fun getByOrderNumber(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(loadOrdersUseCase.getByOrderNumber(orderNumber))
    }

    override fun findAll(): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(loadOrdersUseCase.findAll())
    }

    override fun getByStatus(status: String): ResponseEntity<List<Order>> {
        return ResponseEntity.ok(loadOrdersUseCase.findByStatus(OrderStatus.fromString(status)))
    }

    override fun getByStatusAndCustomer(
        status: String,
        customerNickname: String?,
        customerDocument: String?,
    ): ResponseEntity<List<Order>> {
        val orderStatus = OrderStatus.fromString(status)
        val orders =
            when {
                customerNickname != null ->
                    loadOrdersUseCase.findByCustomerNicknameAndStatus(
                        customerNickname,
                        orderStatus,
                    )

                customerDocument != null ->
                    loadOrdersUseCase.findByCustomerDocumentAndStatus(
                        customerDocument,
                        orderStatus,
                    )

                else -> loadOrdersUseCase.findByStatus(orderStatus)
            }
        return ResponseEntity.ok(orders)
    }

    override fun getByCustomer(
        customerNickname: String?,
        customerDocument: String?,
    ): ResponseEntity<List<Order>> {
        val orders =
            when {
                customerNickname != null -> loadOrdersUseCase.findByCustomerNickname(customerNickname)
                customerDocument != null -> loadOrdersUseCase.findByCustomerDocument(customerDocument)
                else -> loadOrdersUseCase.findAll()
            }
        return ResponseEntity.ok(orders)
    }

    override fun create(orderRequest: OrderRequest): ResponseEntity<Order> {
        return ResponseEntity.ok(
            createOrderUseCase.create(
                orderRequest.customerNickname,
                orderRequest.customerDocument,
                orderRequest.toOrderItemDomain(),
            ),
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
