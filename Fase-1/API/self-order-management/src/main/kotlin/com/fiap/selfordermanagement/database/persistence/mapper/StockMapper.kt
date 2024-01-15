package com.fiap.selfordermanagement.database.persistence.mapper

import com.fiap.selfordermanagement.database.persistence.entities.StockEntity
import com.fiap.selfordermanagement.application.domain.entities.Stock
import org.mapstruct.Mapper

@Mapper
interface StockMapper {
    fun toDomain(entity: StockEntity): Stock

    fun toEntity(domain: Stock): StockEntity
}
