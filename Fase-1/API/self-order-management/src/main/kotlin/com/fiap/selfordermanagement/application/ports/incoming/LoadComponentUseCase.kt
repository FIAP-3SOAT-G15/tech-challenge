package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Component

interface LoadComponentUseCase {
    fun getByComponentNumber(componentNumber: Long): Component

    fun findByProductNumber(productNumber: Long): List<Component>

    fun findAll(): List<Component>
}
