package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.ComponentJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.mapper.ComponentMapper
import com.fiap.selfordermanagement.application.domain.entities.Component
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.ComponentRepository
import org.mapstruct.factory.Mappers

class ComponentRepositoryImpl(
    private val componentJpaRepository: ComponentJpaRepository,
) : ComponentRepository {
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
                ?. let {
                    findByComponentNumber(it)
                        ?. update(component)
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
