package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.CustomerRequest
import com.fiap.selfordermanagement.application.domain.entities.Customer
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Customers")
@RequestMapping("/customers")
interface CustomersAPI {
    @GetMapping
    fun findAll(): ResponseEntity<List<Customer>>

    @GetMapping("/{document}")
    fun getByDocument(
        @PathVariable("document") document: String,
    ): ResponseEntity<Customer?>

    @GetMapping("/search")
    fun searchByName(
        @RequestParam("name") name: String,
    ): ResponseEntity<List<Customer>>

    @PostMapping
    fun create(
        @RequestBody customerRequest: CustomerRequest,
    ): ResponseEntity<Customer>

    @PutMapping("/{document}")
    fun update(
        @PathVariable("document") document: String,
        @RequestBody customerRequest: CustomerRequest,
    ): ResponseEntity<Customer>

    @DeleteMapping("/{document}")
    fun remove(
        @PathVariable("document") document: String,
    ): ResponseEntity<Customer>
}
