package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.StockJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.mapper.StockMapper
import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository
import org.mapstruct.factory.Mappers

class StockRepositoryImpl(
    private val stockJpaRepository: StockJpaRepository,
) : StockRepository {
    private val mapper = Mappers.getMapper(StockMapper::class.java)

    override fun findAll(): List<Stock> {
        return stockJpaRepository.findAll()
            .map(mapper::toDomain)
    }

    override fun findByProductNumber(productNumber: Long): Stock? {
        return stockJpaRepository.findById(productNumber)
            .map(mapper::toDomain)
            .orElse(null)
    }

    // TODO: decide whether to continue using upsert for managing stock
    override fun upsert(stock: Stock): Stock {
        val currentStock =
            stock.productNumber?.let {
                findByProductNumber(productNumber = stock.productNumber)
            } ?: stock.copy(productNumber = null)
        return currentStock
            .let(mapper::toEntity)
            .let(stockJpaRepository::save)
            .let(mapper::toDomain)
    }
}
