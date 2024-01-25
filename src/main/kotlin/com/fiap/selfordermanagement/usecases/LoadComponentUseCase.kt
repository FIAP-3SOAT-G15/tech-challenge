package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Component

interface LoadComponentUseCase {
    fun getByComponentNumber(componentNumber: Long): Component

    fun findByProductNumber(productNumber: Long): List<Component>

    fun findAll(): List<Component>
}
