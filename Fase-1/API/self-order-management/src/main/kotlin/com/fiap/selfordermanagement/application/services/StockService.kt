package com.fiap.selfordermanagement.application.services

import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.incoming.AdjustInventoryUseCase
import com.fiap.selfordermanagement.application.ports.incoming.LoadStockUseCase
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository

class StockService(
    private val stockRepository: StockRepository,
) :
    LoadStockUseCase,
        AdjustInventoryUseCase {
    override fun getByProductNumber(productNumber: Long): Stock {
        return stockRepository.findByProductNumber(productNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.STOCK_NOT_FOUND,
                message = "Stock not found for product $productNumber",
            )
    }

    override fun findAll(): List<Stock> {
        return stockRepository.findAll()
    }

    override fun increment(
        productNumber: Long,
        quantity: Long,
    ): Stock {
        val stock = getByProductNumber(productNumber)
        return stockRepository.upsert(stock.copy(quantity = stock.quantity + quantity))
    }

    override fun decrement(
        productNumber: Long,
        quantity: Long,
    ): Stock {
        val stock = getByProductNumber(productNumber)
        if (stock.hasSufficientInventory(quantity)) {
            throw SelfOrderManagementException(
                errorType = ErrorType.INSUFFICIENT_STOCK,
                message = "Insufficient stock for product ${stock.productNumber}",
            )
        }
        return stockRepository.upsert(stock.copy(quantity = stock.quantity - quantity))
    }
}
