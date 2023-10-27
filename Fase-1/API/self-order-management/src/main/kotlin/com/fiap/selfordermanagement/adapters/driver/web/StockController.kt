package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.StockAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.ports.incoming.AdjustInventoryUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadStockUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val getStockUseCase: LoadStockUseCase,
    private val adjustInventoryUseCase: AdjustInventoryUseCase,
) : StockAPI {
    override fun getByProductNumber(productNumber: Long): ResponseEntity<Stock> {
        return ResponseEntity.ok(getStockUseCase.getByProductNumber(productNumber))
    }

    override fun findAll(): ResponseEntity<List<Stock>> {
        return ResponseEntity.ok(getStockUseCase.findAll())
    }

    override fun increment(
        productNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock> {
        return ResponseEntity.ok(adjustInventoryUseCase.increment(productNumber, quantityRequest.quantity))
    }

    override fun decrement(
        productNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Stock> {
        return ResponseEntity.ok(adjustInventoryUseCase.decrement(productNumber, quantityRequest.quantity))
    }
}
