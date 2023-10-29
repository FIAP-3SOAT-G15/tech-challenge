package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Input

interface LoadStockUseCase {
    fun getByProductNumber(productNumber: Long): List<Input>

    fun findAll(): List<Input>
}
