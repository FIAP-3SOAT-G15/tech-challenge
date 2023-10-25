package com.fiap.selfordermanagement.adapters.driven.persistence.mapper

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.StockEntity
import com.fiap.selfordermanagement.application.domain.entities.Stock
import org.mapstruct.Mapper

@Mapper
interface StockMapper {
    fun toDomain(entity: StockEntity): Stock

    fun toEntity(domain: Stock): StockEntity
}
