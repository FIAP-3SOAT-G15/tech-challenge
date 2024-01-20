package com.fiap.selfordermanagement.application.adapter.controller

import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.usecases.AdjustStockUseCase
import com.fiap.selfordermanagement.web.api.StockAPI
import com.fiap.selfordermanagement.web.request.QuantityRequest
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
