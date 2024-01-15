package com.fiap.selfordermanagement.application.api

import com.fiap.selfordermanagement.application.adapter.request.ItemComposeRequest
import com.fiap.selfordermanagement.application.adapter.request.ItemNameRequest
import com.fiap.selfordermanagement.application.adapter.request.ItemRequest
import com.fiap.selfordermanagement.application.domain.entities.Item
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Items")
@RequestMapping("/items")
interface ItemsAPI {
    @PostMapping()
    fun create(
        @RequestBody item: ItemRequest,
    ): ResponseEntity<Item>

    @PutMapping()
    fun update(
        @RequestBody item: ItemRequest,
    ): ResponseEntity<Item>

    @DeleteMapping
    fun delete(
        @RequestBody item: ItemNameRequest,
    ): ResponseEntity<Item>

    @PostMapping("/compose")
    fun compose(
        @RequestBody item: ItemComposeRequest,
    ): ResponseEntity<Item>
}
