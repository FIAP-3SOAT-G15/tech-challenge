package com.fiap.selfordermanagement.adapter.gateway

import com.fiap.selfordermanagement.domain.entities.Stock

interface StockGateway {
    fun findByComponentNumber(componentNumber: Long): Stock?

    fun create(stock: Stock): Stock

    fun update(stock: Stock): Stock
}
