package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Order
import com.fiap.selfordermanagement.domain.valueobjects.OrderStatus
import com.fiap.selfordermanagement.driver.web.OrdersAPI
import com.fiap.selfordermanagement.driver.web.request.OrderRequest
import com.fiap.selfordermanagement.driver.web.response.OrderToPayResponse
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class OrderController(
    private val loadOrdersUseCase: com.fiap.selfordermanagement.usecases.LoadOrderUseCase,
    private val createOrderUseCase: com.fiap.selfordermanagement.usecases.PlaceOrderUseCase,
    private val loadPaymentUseCase: LoadPaymentUseCase,
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

    override fun getByStatusAndCustomerId(
        status: String,
        customerId: String,
    ): ResponseEntity<List<Order>> {
        val orderStatus = OrderStatus.fromString(status)
        customerId
            .runCatching { UUID.fromString(this) }
            .getOrElse { return ResponseEntity.notFound().build() }
            .let { loadOrdersUseCase.findByCustomerIdAndStatus(it, orderStatus) }
            .run { return ResponseEntity.ok(this) }
    }

    override fun getByCustomerId(customerId: String): ResponseEntity<List<Order>> {
        customerId
            .runCatching { UUID.fromString(this) }
            .getOrElse { return ResponseEntity.notFound().build() }
            .let { loadOrdersUseCase.findByCustomerId(it) }
            .run { return ResponseEntity.ok(this) }
    }

    override fun create(orderRequest: OrderRequest): ResponseEntity<OrderToPayResponse> {
        val order =
            createOrderUseCase.create(
                null, // TODO get from token
                orderRequest.toOrderItemDomain(),
            )
        val payment = loadPaymentUseCase.getByOrderNumber(order.number!!)

        return ResponseEntity.ok(
            OrderToPayResponse(
                order = order,
                paymentInfo = payment.paymentInfo,
            ),
        )
    }

    override fun start(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(prepareOrderUseCase.startOrderPreparation(orderNumber))
    }

    override fun finish(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(prepareOrderUseCase.finishOrderPreparation(orderNumber))
    }

    override fun complete(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(completeOrderUseCase.completeOrder(orderNumber))
    }

    override fun cancel(orderNumber: Long): ResponseEntity<Order> {
        return ResponseEntity.ok(cancelOrderStatusUseCase.cancelOrder(orderNumber))
    }
}
