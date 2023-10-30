package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Component

interface CreateComponentUseCase {
    fun create(
        component: Component,
        initialQuantity: Long,
    ): Component
}
