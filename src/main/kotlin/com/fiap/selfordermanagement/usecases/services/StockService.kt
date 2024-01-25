package services

import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.domain.entities.Stock
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.usecases.AdjustStockUseCase
import com.fiap.selfordermanagement.usecases.LoadStockUseCase

class StockService(
    private val stockRepository: StockGateway,
) : LoadStockUseCase,
    AdjustStockUseCase {
    override fun getByComponentNumber(componentNumber: Long): Stock {
        return stockRepository.findByComponentNumber(componentNumber)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.STOCK_NOT_FOUND,
                message = "Stock not found for component [$componentNumber]",
            )
    }

    override fun increment(
        componentNumber: Long,
        quantity: Long,
    ): Stock {
        val stock = getByComponentNumber(componentNumber)
        return stockRepository.update(stock.copy(quantity = stock.quantity + quantity))
    }

    override fun decrement(
        componentNumber: Long,
        quantity: Long,
    ): Stock {
        val stock = getByComponentNumber(componentNumber)
        if (stock.hasSufficientInventory(quantity)) {
            throw SelfOrderManagementException(
                errorType = ErrorType.INSUFFICIENT_STOCK,
                message = "Insufficient stock for component $componentNumber",
            )
        }
        return stockRepository.update(stock.copy(quantity = stock.quantity - quantity))
    }
}
