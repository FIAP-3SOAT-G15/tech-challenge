package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Component

interface SearchComponentUseCase {
    fun searchByName(componentName: String): List<Component>
}
