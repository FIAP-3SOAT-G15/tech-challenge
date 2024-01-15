package com.fiap.selfordermanagement.application.adapter.controller

import com.fiap.selfordermanagement.web.api.ProductAPI
import com.fiap.selfordermanagement.web.request.ProductComposeRequest
import com.fiap.selfordermanagement.web.request.ProductRequest
import com.fiap.selfordermanagement.web.response.ProductResponse
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory
import com.fiap.selfordermanagement.application.usecases.AssembleProductsUseCase
import com.fiap.selfordermanagement.application.usecases.LoadProductUseCase
import com.fiap.selfordermanagement.application.usecases.SearchProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val assembleProductsUseCase: AssembleProductsUseCase,
    private val loadProductUseCase: LoadProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
) : ProductAPI {
    override fun getByProductNumber(productNumber: Long): ResponseEntity<ProductResponse> {
        return loadProductUseCase.getByProductNumber(productNumber).let(::createResponse)
    }

    override fun findAll(): ResponseEntity<List<ProductResponse>> {
        return loadProductUseCase.findAll().let(::respond)
    }

    override fun findByCategory(category: String): ResponseEntity<List<ProductResponse>> {
        return loadProductUseCase.findByCategory(ProductCategory.fromString(category)).let(::respond)
    }

    override fun searchByName(name: String): ResponseEntity<List<ProductResponse>> {
        return searchProductUseCase.searchByName(name).let(::respond)
    }

    override fun create(productRequest: ProductRequest): ResponseEntity<ProductResponse> {
        val result =
            assembleProductsUseCase.create(productRequest.toDomain(), productRequest.components).let(::createResponse)
        return result
    }

    override fun update(
        productNumber: Long,
        productRequest: ProductRequest,
    ): ResponseEntity<ProductResponse> {
        val product = productRequest.toDomain().copy(number = productNumber)
        return assembleProductsUseCase.update(product, productRequest.components).let(::createResponse)
    }

    override fun delete(productNumber: Long): ResponseEntity<ProductResponse> {
        return assembleProductsUseCase.delete(productNumber).let(::createResponse)
    }

    override fun compose(productComposeRequest: ProductComposeRequest): ResponseEntity<ProductResponse> {
        return assembleProductsUseCase.compose(
            productComposeRequest.productNumber,
            productComposeRequest.subItemsNumbers,
        ).let(::createResponse)
    }

    private fun createResponse(product: Product?): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(product?.let { ProductResponse.fromDomain(product) })
    }

    private fun respond(products: List<Product>): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(products.map { ProductResponse.fromDomain(it) })
    }
}
