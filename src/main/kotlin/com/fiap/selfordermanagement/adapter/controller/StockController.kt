package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Stock
import com.fiap.selfordermanagement.usecases.AdjustStockUseCase
import com.fiap.selfordermanagement.driver.web.StockAPI
import com.fiap.selfordermanagement.driver.web.request.QuantityRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val adjustStockUseCase: com.fiap.selfordermanagement.usecases.AdjustStockUseCase,
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
