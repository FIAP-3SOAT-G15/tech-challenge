package com.fiap.selfordermanagement.application.adapter.services

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory
import com.fiap.selfordermanagement.application.adapter.repository.ProductRepository
import com.fiap.selfordermanagement.application.usecases.*

class ProductService(
    private val productRepository: ProductRepository,
    private val loadComponentUseCase: LoadComponentUseCase,
) :
    LoadProductUseCase,
    SearchProductUseCase,
    AssembleProductsUseCase,
    RemoveProductUseCase {
    override fun getByProductNumber(productNumber: Long): Product {
        return productRepository.findByProductNumber(productNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_NOT_FOUND,
                message = "Product [$productNumber] not found",
            )
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun findByCategory(category: ProductCategory): List<Product> {
        return productRepository.findByCategory(category)
    }

    override fun searchByName(productName: String): List<Product> {
        return productRepository.searchByName(productName.trim())
    }

    override fun create(
        product: Product,
        components: List<Long>,
    ): Product {
        val newProduct = product.copy(components = components.map(loadComponentUseCase::getByComponentNumber))
        return productRepository.create(newProduct)
    }

    override fun update(
        product: Product,
        components: List<Long>,
    ): Product {
        val newProduct = product.copy(components = components.map(loadComponentUseCase::getByComponentNumber))
        return productRepository.update(newProduct)
    }

    override fun delete(productNumber: Long): Product {
        return productRepository.delete(productNumber)
    }

    override fun compose(
        productNumber: Long,
        subItemsNumbers: List<Long>,
    ): Product {
        val product = getByProductNumber(productNumber)
        val subItems = subItemsNumbers.map(::getByProductNumber)
        val newProduct = product.copy(subItems = subItems)
        return productRepository.update(newProduct)
    }
}
