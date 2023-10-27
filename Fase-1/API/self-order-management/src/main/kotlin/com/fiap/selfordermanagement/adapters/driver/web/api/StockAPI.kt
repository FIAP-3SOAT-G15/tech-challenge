package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.application.domain.entities.Stock
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Stock")
@RequestMapping("/stock")
interface StockAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Stock>>

    @GetMapping("/{productNumber}")
    fun getByProductNumber(
        @PathVariable productNumber: Long,
    ): ResponseEntity<Stock>

    @PostMapping("/{productNumber}/increment")
    fun increment(
        @PathVariable productNumber: Long,
        @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock>

    @PostMapping("/{productNumber}/decrement")
    fun decrement(
        @PathVariable productNumber: Long,
        @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock>
}
