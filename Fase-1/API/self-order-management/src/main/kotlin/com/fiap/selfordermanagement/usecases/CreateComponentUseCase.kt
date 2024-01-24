package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Component

interface CreateComponentUseCase {
    fun create(
        component: Component,
        initialQuantity: Long,
    ): Component
}
