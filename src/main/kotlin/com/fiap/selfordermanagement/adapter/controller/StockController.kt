package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Stock
import com.fiap.selfordermanagement.driver.web.StockAPI
import com.fiap.selfordermanagement.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.usecases.AdjustStockUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val adjustStockUseCase: AdjustStockUseCase,
) : StockAPI {
    override fun increment(
        componentNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock> {
        return ResponseEntity.ok(adjustStockUseCase.increment(componentNumber, quantityRequest.quantity))
    }

    override fun decrement(
        componentNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock> {
        return ResponseEntity.ok(adjustStockUseCase.decrement(componentNumber, quantityRequest.quantity))
    }
}
