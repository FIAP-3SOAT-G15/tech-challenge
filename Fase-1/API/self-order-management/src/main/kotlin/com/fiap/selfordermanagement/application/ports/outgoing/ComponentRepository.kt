package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Component

interface ComponentRepository {
    fun findAll(): List<Component>

    fun findByComponentNumber(componentNumber: Long): Component?

    fun searchByName(componentName: String): List<Component>

    fun create(component: Component): Component

    fun update(component: Component): Component

    fun delete(component: Component): Component

    fun deleteAll()
}
