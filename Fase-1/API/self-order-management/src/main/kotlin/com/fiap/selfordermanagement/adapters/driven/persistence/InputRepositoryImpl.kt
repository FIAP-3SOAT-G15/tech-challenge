package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.InputJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.mapper.InputMapper
import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import com.fiap.selfordermanagement.application.ports.outgoing.InputRepository
import org.mapstruct.factory.Mappers

class InputRepositoryImpl(private val inputJpaRepository: InputJpaRepository) : InputRepository {
    private val mapper = Mappers.getMapper(InputMapper::class.java)

    override fun findByInputNumber(inputNumber: Long): Input {
        return inputJpaRepository.findById(inputNumber)
            .map(mapper::toDomain).orElse(null)
            ?: throw SelfOrderManagementException(
                errorType = ErrorType.STOCK_NOT_FOUND,
                message = "Not found stock for input $inputNumber",
            )
    }

    override fun findAll(): List<Input> {
        return inputJpaRepository.findAll().map(mapper::toDomain)
    }

    override fun update(input: Input): Input {
        return input.number!!.let(this::findByInputNumber)
            ?.let { it.update(input) }
            ?.let { inputJpaRepository.save(mapper.toEntity(it)) }
            ?.let(mapper::toDomain) ?: throw SelfOrderManagementException(
            errorType = ErrorType.STOCK_NOT_FOUND,
            message = "Not found stock for input ${input.name}",
        )
    }

    override fun create(input: Input): Input {
        val inputEntity =
            input.copy(number = null)
                .let(mapper::toEntity)
        val stockEntity = inputEntity.stock
        val inputEntitySaved = inputJpaRepository.save(inputEntity.withStock(null))
        return (
            stockEntity?.let {
                inputJpaRepository.save(inputEntitySaved.withStock(it.withNumber(inputEntitySaved.number!!)))
            } ?: inputEntitySaved
        ).let(mapper::toDomain)
    }

    override fun findByName(inputName: String): List<Input> {
        return inputJpaRepository.findByNameContainingOrderByName(name = inputName)
            .map(mapper::toDomain)
    }
}
