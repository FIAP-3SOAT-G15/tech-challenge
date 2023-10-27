package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.ProductJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.mapper.ProductMapper
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository
import org.mapstruct.factory.Mappers

class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    private val mapper: ProductMapper = Mappers.getMapper(ProductMapper::class.java)

    override fun findAll(): List<Product> {
        return productJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun findByProductNumber(productNumber: Long): Product? {
        return productJpaRepository.findById(productNumber)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun searchByName(name: String): List<Product> {
        return productJpaRepository.findByNameContains(name)
            .map(mapper::toDomain)
    }

    override fun create(product: Product): Product {
        findByProductNumber(product.number)?.let {
            throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_ALREADY_EXISTS,
                message = "Product ${product.number} ${product.name} already exists",
            )
        }
        return persist(product)
    }

    override fun update(product: Product): Product {
        val newItem =
            findByProductNumber(product.number)?.update(product)
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.PRODUCT_NOT_FOUND,
                    message = "Product ${product.number} ${product.name} not found",
                )
        return persist(newItem)
    }

    override fun delete(productNumber: Long): Product {
        return findByProductNumber(productNumber)?.let {
            productJpaRepository.deleteById(productNumber)
            it
        }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_NOT_FOUND,
                message = "Product $productNumber not found",
            )
    }

    private fun persist(product: Product): Product =
        product
            .let(mapper::toEntity)
            .let(productJpaRepository::save)
            .let(mapper::toDomain)
}