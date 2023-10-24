package com.fiap.selfordermanagement.adapter.driven.repository.mapper

import com.fiap.selfordermanagement.adapter.driven.repository.entities.StockEntity
import com.fiap.selfordermanagement.core.domain.entities.Stock
import org.mapstruct.Mapper

@Mapper
interface StockMapper {

    fun toDomain(entity: StockEntity) : Stock

    fun toEntity(domain: Stock) : StockEntity

}