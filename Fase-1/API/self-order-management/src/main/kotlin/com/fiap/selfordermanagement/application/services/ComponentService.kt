package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Component
import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.incoming.CreateComponentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadComponentUseCase
import com.fiap.selfordermanagement.application.ports.incoming.SearchComponentUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.ComponentRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository

class ComponentService(
    private val componentRepository: ComponentRepository,
    private val stockRepository: StockRepository,
    private val productRepository: ProductRepository,
) : LoadComponentUseCase,
    SearchComponentUseCase,
    CreateComponentUseCase {
    override fun getByComponentNumber(componentNumber: Long): Component {
        return componentRepository.findByComponentNumber(componentNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.COMPONENT_NOT_FOUND,
                message = "Component [$componentNumber] not found",
            )
    }

    override fun findByProductNumber(productNumber: Long): List<Component> {
        return productRepository.findByProductNumber(productNumber)
            ?. components
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_NOT_FOUND,
                message = "Product [$productNumber] not found",
            )
    }

    override fun searchByName(componentName: String): List<Component> {
        return componentRepository.searchByName(componentName)
    }

    override fun findAll(): List<Component> {
        return componentRepository.findAll()
    }

    override fun create(
        component: Component,
        initialQuantity: Long,
    ): Component {
        val savedComponent = componentRepository.create(component)
        val stock = Stock(componentNumber = savedComponent.number!!, quantity = initialQuantity)
        stockRepository.create(stock)
        return savedComponent
    }
}
