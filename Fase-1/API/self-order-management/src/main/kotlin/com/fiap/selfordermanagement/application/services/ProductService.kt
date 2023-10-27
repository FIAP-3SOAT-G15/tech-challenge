package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.incoming.AssembleProductsUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadProductUseCase
import com.fiap.selfordermanagement.application.ports.incoming.RemoveProductUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchProductUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository

class ProductService(
    private val productRepository: ProductRepository,
) :
    LoadProductUseCase,
        SearchProductUseCase,
        AssembleProductsUseCase,
        RemoveProductUseCase {
    override fun getByProductNumber(productNumber: Long): Product {
        return productRepository.findByProductNumber(productNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_NOT_FOUND,
                message = "Product $productNumber not found",
            )
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun searchByName(productName: String): List<Product> {
        return productRepository.searchByName(productName)
    }

    override fun create(product: Product): Product {
        return productRepository.create(product)
    }

    override fun update(product: Product): Product {
        return productRepository.update(product)
    }

    override fun delete(productNumber: Long): Product {
        return productRepository.delete(productNumber)
    }

    override fun compose(
        productNumber: Long,
        subItemsName: List<Long>,
    ): Product? {
        val items = subItemsName.mapNotNull(productRepository::findByProductNumber)
        return productRepository.findByProductNumber(productNumber)?.let {
            it.copy(subItem = it.subItem.plus(items))
        }?.let(productRepository::update)
    }
}
