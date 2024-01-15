package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Component

interface SearchComponentUseCase {
    fun searchByName(componentName: String): List<Component>
}
