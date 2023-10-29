package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.NewInputRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.application.domain.entities.Input
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Stock")
@RequestMapping("/admin/stock")
interface StockAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Input>>

    @GetMapping("/{productNumber}")
    fun getByProductNumber(
        @PathVariable productNumber: Long,
    ): ResponseEntity<List<Input>>

    @PostMapping("/{inputNumber}/increment")
    fun increment(
        @PathVariable inputNumber: Long,
        @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Input>

    @PostMapping("/{inputNumber}/decrement")
    fun decrement(
        @PathVariable inputNumber: Long,
        @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Input>

    @PostMapping()
    fun create(
        @RequestBody newInputRequest: NewInputRequest,
    ): ResponseEntity<Input>

    @GetMapping("/search")
    fun searchByName(
        @RequestParam("name") name: String,
    ): ResponseEntity<List<Input>>
}
