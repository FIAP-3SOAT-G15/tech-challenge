package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Stock

interface LoadStockUseCase {
    fun getByProductNumber(productNumber: Long): Stock

    fun findAll(): List<Stock>
}
