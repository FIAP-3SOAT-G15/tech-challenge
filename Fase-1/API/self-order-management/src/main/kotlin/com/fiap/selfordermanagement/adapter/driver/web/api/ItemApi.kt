package com.fiap.selfordermanagement.adapter.driver.web.api

import com.fiap.selfordermanagement.adapter.driver.web.request.ItemRequest
import com.fiap.selfordermanagement.core.domain.entities.Item
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/self_orders/items")
interface ItemApi {

    @PostMapping()
    fun create(@RequestBody item: ItemRequest) : ResponseEntity<Item>

    @PostMapping("/{itemName}")
    fun compose(@PathVariable itemName: String, @RequestBody item: List<ItemRequest>) : ResponseEntity<Item>
}