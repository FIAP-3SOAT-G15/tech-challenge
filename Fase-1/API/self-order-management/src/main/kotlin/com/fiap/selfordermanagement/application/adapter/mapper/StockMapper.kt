package com.fiap.selfordermanagement.application.adapter.mapper

import com.fiap.selfordermanagement.application.domain.entities.Stock
import com.fiap.selfordermanagement.infra.provider.persistence.entities.StockEntity
import org.mapstruct.Mapper

@Mapper
interface StockMapper {
    fun toDomain(entity: StockEntity): Stock

    fun toEntity(domain: Stock): StockEntity
}
