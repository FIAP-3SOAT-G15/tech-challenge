package com.fiap.selfordermanagement.adapter.controller

import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.driver.web.ComponentAPI
import com.fiap.selfordermanagement.driver.web.request.ComponentRequest
import com.fiap.selfordermanagement.usecases.CreateComponentUseCase
import com.fiap.selfordermanagement.usecases.LoadComponentUseCase
import com.fiap.selfordermanagement.usecases.SearchComponentUseCase
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
        return ResponseEntity.ok(
            createComponentUseCase.create(
                componentRequest.toComponent(),
                componentRequest.initialQuantity,
            ),
        )
    }

    override fun searchByName(name: String): ResponseEntity<List<Component>> {
        return ResponseEntity.ok(searchComponentUseCase.searchByName(name.trim()))
    }
}
