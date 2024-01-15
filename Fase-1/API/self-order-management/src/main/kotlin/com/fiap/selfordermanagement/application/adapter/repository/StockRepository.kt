package com.fiap.selfordermanagement.application.adapter.repository

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface StockRepository {
    fun findByComponentNumber(componentNumber: Long): Stock?

    fun create(stock: Stock): Stock

    fun update(stock: Stock): Stock
}
