package com.fiap.selfordermanagement.application.adapter.controller

import com.fiap.selfordermanagement.web.api.PaymentAPI
import com.fiap.selfordermanagement.application.domain.entities.Payment
import com.fiap.selfordermanagement.application.usecases.LoadPaymentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PaymentController(
    private val loadPaymentUseCase: LoadPaymentUseCase,
) : PaymentAPI {
    override fun findAll(): ResponseEntity<List<Payment>> {
        return ResponseEntity.ok(loadPaymentUseCase.findAll())
    }

    override fun getByOrderNumber(orderNumber: Long): ResponseEntity<Payment> {
        return ResponseEntity.ok(loadPaymentUseCase.getByOrderNumber(orderNumber))
    }
}
