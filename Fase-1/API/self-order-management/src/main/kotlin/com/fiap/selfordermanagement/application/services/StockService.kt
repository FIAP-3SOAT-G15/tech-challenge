package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.incoming.AdjustInventoryUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadStockUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.InputRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository

class StockService(
    private val productRepository: ProductRepository,
    private val inputRepository: InputRepository,
) :
    LoadStockUseCase,
        AdjustInventoryUseCase {
    override fun getByProductNumber(productNumber: Long): List<Input> {
        return productRepository.findByProductNumber(productNumber)?.let { it.inputs }
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.PRODUCT_NOT_FOUND,
                message = "Product not found for product $productNumber",
            )
    }

    override fun findAll(): List<Input> {
        return inputRepository.findAll()
    }

    override fun increment(
        inputNumber: Long,
        quantity: Long,
    ): Input {
        val input = findInput(inputNumber)
        return inputRepository.update(input.copy(stock = input.stock.copy(quantity = input.stock.quantity + quantity)))
    }

    private fun findInput(inputNumber: Long) =
        inputRepository.findByInputNumber(inputNumber) ?: throw SelfOrderManagementException(
            errorType = ErrorType.STOCK_NOT_FOUND,
            message = "Stock not found for input $inputNumber",
        )

    override fun decrement(
        inputNumber: Long,
        quantity: Long,
    ): Input {
        val input = findInput(inputNumber)
        if (input.stock.hasSufficientInventory(quantity)) {
            throw SelfOrderManagementException(
                errorType = ErrorType.INSUFFICIENT_STOCK,
                message = "Insufficient stock for product $inputNumber",
            )
        }
        return inputRepository.update(input.copy(stock = input.stock.copy(quantity = input.stock.quantity - quantity)))
    }

    override fun createInput(input: Input): Input {
        return inputRepository.create(input)
    }

    override fun findInput(name: String): List<Input> {
        return inputRepository.findByName(name)
    }
}
