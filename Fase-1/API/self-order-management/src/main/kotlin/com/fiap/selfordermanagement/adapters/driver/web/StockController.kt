package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.StockAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.NewInputRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.ports.incoming.AdjustInventoryUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadStockUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val getStockUseCase: LoadStockUseCase,
    private val adjustInventoryUseCase: AdjustInventoryUseCase,
) : StockAPI {
    override fun getByProductNumber(productNumber: Long): ResponseEntity<List<Input>> {
        return ResponseEntity.ok(getStockUseCase.getByProductNumber(productNumber))
    }

    override fun findAll(): ResponseEntity<List<Input>> {
        return ResponseEntity.ok(getStockUseCase.findAll())
    }

    override fun increment(
        inputNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Input> {
        return ResponseEntity.ok(adjustInventoryUseCase.increment(inputNumber, quantityRequest.quantity))
    }

    override fun decrement(
        inputNumber: Long,
        quantityRequest: QuantityRequest,
    ): ResponseEntity<Input> {
        return ResponseEntity.ok(adjustInventoryUseCase.decrement(inputNumber, quantityRequest.quantity))
    }

    override fun create(newInputRequest: NewInputRequest): ResponseEntity<Input> {
        return ResponseEntity.ok(adjustInventoryUseCase.createInput(newInputRequest.toDomain()))
    }

    override fun getByName(query: String): ResponseEntity<List<Input>> {
        return ResponseEntity.ok(adjustInventoryUseCase.findInput(query))
    }
}
