package com.fiap.selfordermanagement.adapter.gateway.impl

import com.fiap.selfordermanagement.adapter.gateway.ComponentGateway
import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.driver.database.persistence.jpa.ComponentJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.mapper.ComponentMapper
import org.mapstruct.factory.Mappers

class ComponentGatewayImpl(
    private val componentJpaRepository: ComponentJpaRepository,
) : ComponentGateway {
    private val mapper = Mappers.getMapper(ComponentMapper::class.java)

    override fun findByComponentNumber(componentNumber: Long): Component? {
        return componentJpaRepository.findById(componentNumber)
            .map(mapper::toDomain)
            .orElse(null)
    }

    override fun findAll(): List<Component> {
        return componentJpaRepository.findAll().map(mapper::toDomain)
    }

    override fun searchByName(componentName: String): List<Component> {
        return componentJpaRepository.findByNameContainingIgnoreCase(componentName)
            .map(mapper::toDomain)
    }

    override fun create(component: Component): Component {
        val newComponent = component.copy(number = null)
        return persist(newComponent)
    }

    override fun update(component: Component): Component {
        val newItem =
            component.number
                ?.let {
                    findByComponentNumber(it)
                        ?.update(component)
                        ?: throw SelfOrderManagementException(
                            errorType = ErrorType.COMPONENT_NUMBER_IS_MANDATORY,
                            message = "Component ${component.name} not identified by number",
                        )
                }
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.COMPONENT_NOT_FOUND,
                    message = "Component [${component.name}] not found",
                )
        return persist(newItem)
    }

    override fun delete(component: Component): Component {
        val number =
            component.number ?: throw SelfOrderManagementException(
                errorType = ErrorType.COMPONENT_NUMBER_IS_MANDATORY,
                message = "Component ${component.name} not identified by number",
            )
        val item =
            findByComponentNumber(number)
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.COMPONENT_NOT_FOUND,
                    message = "Component [${component.number}] not found",
                )
        componentJpaRepository.deleteById(item.number!!)
        return component
    }

    override fun deleteAll() {
        componentJpaRepository.deleteAll()
    }

    private fun persist(component: Component): Component =
        component
            .let(mapper::toEntity)
            .let(componentJpaRepository::save)
            .let(mapper::toDomain)
}
