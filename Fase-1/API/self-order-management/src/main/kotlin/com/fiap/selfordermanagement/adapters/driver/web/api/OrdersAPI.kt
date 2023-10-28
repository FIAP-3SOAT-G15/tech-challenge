package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.OrderRequest
import com.fiap.selfordermanagement.adapters.driver.web.response.PaymentRequestResponse
import com.fiap.selfordermanagement.application.domain.entities.Order
import com.fiap.selfordermanagement.application.domain.valueobjects.OrderStatus
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Orders")
@RequestMapping("/orders")
interface OrdersAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Order>>

    @GetMapping("/{orderNumber}")
    fun getByOrderNumber(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>

    @GetMapping("/{nickname}")
    fun findByCustomerNickname(
        @PathVariable nickname: String
    ): ResponseEntity<List<Order>>

    @GetMapping("/{nickname}/{status}")
    fun findByCustomerNicknameAndStatus(
        @PathVariable nickname: String,
        @PathVariable status: OrderStatus,
    ): ResponseEntity<List<Order>>

    @GetMapping("/{document}")
    fun findByCustomerDocument(
        @PathVariable document: String
    ): ResponseEntity<List<Order>>


    @GetMapping("/{document}/{status}")
    fun findByCustomerDocumentAndStatus(
        @PathVariable document: String,
        @PathVariable status: OrderStatus,
    ): ResponseEntity<List<Order>>

    @GetMapping("/status/{status}")
    fun getByStatus(
        @PathVariable status: String,
    ): ResponseEntity<List<Order>>

    @PostMapping
    fun create(
        @RequestBody orderRequest: OrderRequest,
    ): ResponseEntity<Order>

    @PostMapping("/{orderNumber}/pay")
    fun pay(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<PaymentRequestResponse>

    @PostMapping("/{orderNumber}/confirm")
    fun confirm(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>

    @PostMapping("/{orderNumber}/start")
    fun startPreparation(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>

    @PostMapping("/{orderNumber}/finish")
    fun finishPreparation(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>

    @PostMapping("/{orderNumber}/complete")
    fun complete(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>

    @PostMapping("/{orderNumber}/cancel")
    fun cancel(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Order>
}
