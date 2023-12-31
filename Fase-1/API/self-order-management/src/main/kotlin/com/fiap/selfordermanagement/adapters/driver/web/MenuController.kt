package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.MenuAPI
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory
import com.fiap.selfordermanagement.application.ports.incoming.LoadProductUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class MenuController(
    private val loadProductUseCase: LoadProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
) : MenuAPI {
    override fun findAll(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(loadProductUseCase.findAll())
    }

    override fun findByCategory(category: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(loadProductUseCase.findByCategory(ProductCategory.fromString(category)))
    }

    override fun searchByName(name: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(searchProductUseCase.searchByName(name))
    }
}
