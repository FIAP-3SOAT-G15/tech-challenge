package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Component

interface SearchComponentUseCase {
    fun searchByName(componentName: String): List<Component>
}
