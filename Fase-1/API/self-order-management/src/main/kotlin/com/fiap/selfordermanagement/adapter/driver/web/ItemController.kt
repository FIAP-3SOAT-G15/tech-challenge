package com.fiap.selfordermanagement.adapter.driver.web

import com.fiap.selfordermanagement.adapter.driver.web.api.ItemApi
import com.fiap.selfordermanagement.adapter.driver.web.request.ItemRequest
import com.fiap.selfordermanagement.core.application.use_cases.AssembleProductsUseCase
import com.fiap.selfordermanagement.core.domain.entities.Item
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemController(
    private val assembleProductsUseCase: AssembleProductsUseCase
) : ItemApi {

    override fun create(item: ItemRequest): ResponseEntity<Item> {
        return ResponseEntity.ok(assembleProductsUseCase.create(item.toDomain()))
    }

    override fun compose(itemName: String, items: List<ItemRequest>): ResponseEntity<Item> {
        return ResponseEntity.ok(assembleProductsUseCase.compose(itemName, items.map { it.toDomain() }))
    }
}