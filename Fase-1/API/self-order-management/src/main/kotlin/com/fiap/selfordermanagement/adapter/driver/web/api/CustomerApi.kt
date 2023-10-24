package com.fiap.selfordermanagement.adapter.driver.web.api

import com.fiap.selfordermanagement.core.domain.entities.Customer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/self_orders/customers")
interface CustomerApi {
    @GetMapping()
    fun getAll(): List<Customer>

    @GetMapping("/{document}")
    fun search(
        @PathVariable("document") document: String,
    ): Customer?
}
