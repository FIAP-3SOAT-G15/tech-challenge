package com.fiap.selfordermanagement.database.persistence

import com.fiap.selfordermanagement.application.adapter.repository.StockRepository
import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.database.persistence.jpa.StockJpaRepository
import com.fiap.selfordermanagement.database.persistence.mapper.StockMapper
import org.mapstruct.factory.Mappers

class StockRepositoryImpl(
    private val stockJpaRepository: StockJpaRepository,
) : StockRepository {
    private val mapper = Mappers.getMapper(StockMapper::class.java)

    override fun findByComponentNumber(componentNumber: Long): Stock? {
        return stockJpaRepository.findById(componentNumber)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun create(stock: Stock): Stock {
        findByComponentNumber(stock.componentNumber)?.let {
            throw SelfOrderManagementException(
                errorType = ErrorType.STOCK_ALREADY_EXISTS,
                message = "Stock record for component [${stock.componentNumber}] already exists",
            )
        }
        return persist(stock)
    }

    override fun update(stock: Stock): Stock {
        val newItem =
            findByComponentNumber(stock.componentNumber)
                ?.update(stock)
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.STOCK_NOT_FOUND,
                    message = "Stock [${stock.componentNumber}] not found",
                )
        return persist(newItem)
    }

    private fun persist(stock: Stock): Stock =
        stock
            .let(mapper::toEntity)
            .let(stockJpaRepository::save)
            .let(mapper::toDomain)
}
