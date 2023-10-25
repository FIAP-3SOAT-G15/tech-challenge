package com.fiap.selfordermanagement.adapter.driver.web

import com.fiap.selfordermanagement.adapter.driver.web.api.ItemsAPI
import com.fiap.selfordermanagement.adapter.driver.web.request.ItemComposeRequest
import com.fiap.selfordermanagement.adapter.driver.web.request.ItemNameRequest
import com.fiap.selfordermanagement.adapter.driver.web.request.ItemRequest
import com.fiap.selfordermanagement.core.application.usecases.AssembleProductsUseCase
import com.fiap.selfordermanagement.core.domain.entities.Item
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val assembleProductsUseCase: AssembleProductsUseCase,
) : ItemsAPI {
    override fun create(item: ItemRequest): ResponseEntity<Item> {
        return ResponseEntity.ok(assembleProductsUseCase.create(item.toDomain()))
    }

    override fun update(item: ItemRequest): ResponseEntity<Item> {
        return ResponseEntity.ok(assembleProductsUseCase.update(item.toDomain()))
    }

    override fun delete(item: ItemNameRequest): ResponseEntity<Item> {
        return ResponseEntity.ok(assembleProductsUseCase.remove(item.name))
    }

    override fun compose(itemCompose: ItemComposeRequest): ResponseEntity<Item> {
        return ResponseEntity.ok(
            assembleProductsUseCase.compose(itemCompose.name, itemCompose.items),
        )
    }
}
