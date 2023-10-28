package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.application.domain.entities.Payment
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Payments")
@RequestMapping("/payments")
interface PaymentAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Payment>>

    @GetMapping("/{orderNumber}")
    fun getByOrderNumber(
        @PathVariable orderNumber: Long,
    ): ResponseEntity<Payment>
}
