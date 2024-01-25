package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Payment
import com.fiap.selfordermanagement.usecases.LoadPaymentUseCase
import com.fiap.selfordermanagement.driver.web.PaymentAPI
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val loadPaymentUseCase: com.fiap.selfordermanagement.usecases.LoadPaymentUseCase,
) : PaymentAPI {
    override fun findAll(): ResponseEntity<List<Payment>> {
        return ResponseEntity.ok(loadPaymentUseCase.findAll())
    }

    override fun getByOrderNumber(orderNumber: Long): ResponseEntity<Payment> {
        return ResponseEntity.ok(loadPaymentUseCase.getByOrderNumber(orderNumber))
    }
}
