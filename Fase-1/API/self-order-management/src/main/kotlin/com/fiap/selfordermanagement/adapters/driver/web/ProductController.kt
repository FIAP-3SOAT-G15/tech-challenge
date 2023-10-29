package com.fiap.selfordermanagement.adapters.driver.web

import com.fiap.selfordermanagement.adapters.driver.web.api.ProductAPI
import com.fiap.selfordermanagement.adapters.driver.web.request.ProductComposeRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.ProductRequest
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.ports.incoming.AssembleProductsUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadProductUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val assembleProductsUseCase: AssembleProductsUseCase,
    private val getProductUseCase: LoadProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
) : ProductAPI {
    override fun getByProductNumber(productNumber: Long): ResponseEntity<Product> {
        return ResponseEntity.ok(getProductUseCase.getByProductNumber(productNumber))
    }

    override fun findAll(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(getProductUseCase.findAll())
    }

    override fun searchByName(query: String): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(searchProductUseCase.searchByName(query))
    }

    override fun create(productRequest: ProductRequest): ResponseEntity<Product> {
        return ResponseEntity.ok(assembleProductsUseCase.create(productRequest.toDomain(), productRequest.inputs))
    }

    override fun update(
        productNumber: Long,
        productRequest: ProductRequest,
    ): ResponseEntity<Product> {
        val identifiedProductRequest = productRequest.copy(number = productNumber)
        return ResponseEntity.ok(assembleProductsUseCase.update(identifiedProductRequest.toDomain(), productRequest.inputs))
    }

    override fun delete(productNumber: Long): ResponseEntity<Product> {
        return ResponseEntity.ok(assembleProductsUseCase.delete(productNumber))
    }

    override fun compose(productComposeRequest: ProductComposeRequest): ResponseEntity<Product> {
        return ResponseEntity.ok(
            assembleProductsUseCase.compose(productComposeRequest.productNumber, productComposeRequest.items),
        )
    }
}
