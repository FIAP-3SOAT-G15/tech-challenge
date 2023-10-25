package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.application.domain.entities.Customer
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Customers")
@RequestMapping("/customers")
interface CustomersAPI {
    @GetMapping()
    fun getAll(): List<Customer>

    @GetMapping("/{document}")
    fun search(
        @PathVariable("document") document: String,
    ): Customer?
}
