package com.fiap.selfordermanagement.application.adapter

import com.fiap.selfordermanagement.application.adapter.mapper.StockMapper
import com.fiap.selfordermanagement.application.adapter.repository.StockRepository
import com.fiap.selfordermanagement.infra.provider.jpa.StockJpaRepository
import org.mapstruct.factory.Mappers

class StockRepositoryImpl(private val stockJpaRepository: StockJpaRepository) : StockRepository {
    private val mapperStock = Mappers.getMapper(StockMapper::class.java)

    override fun increment(
        item: String,
        quantity: Long,
    ) {
        item
            .let(stockJpaRepository::findById)
            .map { mapperStock.toDomain(it) }
            .map { it.copy(quantity = it.quantity + quantity) }
            .map { stockJpaRepository.save(mapperStock.toEntity(it)) }
    }

    override fun decrement(
        item: String,
        quantity: Long,
    ) {
        item
            .let(stockJpaRepository::findById)
            .map { mapperStock.toDomain(it) }
            .map {
                if (isThereAvailable(item, quantity)) {
                    stockJpaRepository.save(mapperStock.toEntity(it.copy(quantity = it.quantity - quantity)))
                }
            }
    }

    override fun isThereAvailable(
        item: String,
        quantity: Long,
    ): Boolean {
        return item
            .let(stockJpaRepository::findById)
            .map { mapperStock.toDomain(it) }
            .map { it.quantity >= quantity }
            .orElse(false)
    }
}
