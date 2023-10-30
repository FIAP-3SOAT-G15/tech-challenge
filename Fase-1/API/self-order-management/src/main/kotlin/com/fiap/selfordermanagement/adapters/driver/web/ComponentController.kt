package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.ComponentAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.ComponentRequest
import com.fiap.selfordermanagement.application.domain.entities.Component
import com.fiap.selfordermanagement.application.ports.incoming.CreateComponentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadComponentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchComponentUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ComponentController(
    private val loadComponentUseCase: LoadComponentUseCase,
    private val createComponentUseCase: CreateComponentUseCase,
    private val searchComponentUseCase: SearchComponentUseCase,
) : ComponentAPI {
    override fun findAll(): ResponseEntity<List<Component>> {
        return ResponseEntity.ok(loadComponentUseCase.findAll())
    }

    override fun findByProductNumber(productNumber: Long): ResponseEntity<List<Component>> {
        return ResponseEntity.ok(loadComponentUseCase.findByProductNumber(productNumber))
    }

    override fun create(componentRequest: ComponentRequest): ResponseEntity<Component> {
        return ResponseEntity.ok(createComponentUseCase.create(componentRequest.toComponent(), componentRequest.initialQuantity))
    }

    override fun searchByName(name: String): ResponseEntity<List<Component>> {
        return ResponseEntity.ok(searchComponentUseCase.searchByName(name.trim()))
    }
}
