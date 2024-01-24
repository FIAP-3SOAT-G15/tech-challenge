package com.fiap.selfordermanagement.driver.database.persistence.mapper

import com.fiap.selfordermanagement.domain.entities.Stock
import com.fiap.selfordermanagement.driver.database.persistence.entities.StockEntity
import org.mapstruct.Mapper

@Mapper
interface StockMapper {
    fun toDomain(entity: StockEntity): Stock

    fun toEntity(domain: Stock): StockEntity
}
