package com.fiap.selfordermanagement.adapter.driver.web.api

import com.fiap.selfordermanagement.core.domain.entities.Client
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/self_orders/clients")
interface ClientApi {
    @GetMapping()
    fun getAllClient(): List<Client>

    @GetMapping("/{document}")
    fun searchClient(
        @PathVariable("document") document: String,
    ): Client?
}
