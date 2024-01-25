package com.fiap.selfordermanagement.domain.entities

data class Component(
    val number: Long? = null,
    val name: String,
) {
    fun update(newComponent: Component): Component =
        copy(
            name = newComponent.name,
        )
}
