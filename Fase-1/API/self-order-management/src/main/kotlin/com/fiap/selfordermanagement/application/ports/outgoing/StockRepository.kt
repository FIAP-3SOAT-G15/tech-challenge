package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface StockRepository {
    fun findAll(): List<Stock>

    fun findByProductNumber(productNumber: Long): Stock?

    fun upsert(stock: Stock): Stock
}
