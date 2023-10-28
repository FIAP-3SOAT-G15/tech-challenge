package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.ProductComposeRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.ProductRequest
import com.fiap.selfordermanagement.application.domain.entities.Product
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Products")
@RequestMapping("/products")
interface ProductAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Product>>

    @GetMapping("/{productNumber}")
    fun getByProductNumber(
        @PathVariable("productNumber") productNumber: Long,
    ): ResponseEntity<Product>

    @GetMapping("/search")
    fun searchByName(
        @PathParam("query") query: String,
    ): ResponseEntity<List<Product>>

    @PostMapping
    fun create(
        @RequestBody productRequest: ProductRequest,
    ): ResponseEntity<Product>

    @PutMapping("/{productNumber}")
    fun update(
        @PathVariable productNumber: Long,
        @RequestBody productRequest: ProductRequest,
    ): ResponseEntity<Product>

    @DeleteMapping("/{productNumber}")
    fun delete(
        @PathVariable("productNumber") productNumber: Long,
    ): ResponseEntity<Product>

    @PostMapping("/compose")
    fun compose(
        @RequestBody productComposeRequest: ProductComposeRequest,
    ): ResponseEntity<Product>
}
