package com.fiap.selfordermanagement.application.ports.outgoing

import com.fiap.selfordermanagement.application.domain.entities.Input

interface InputRepository {
    fun findByInputNumber(inputNumber: Long): Input

    fun findAll(): List<Input>

    fun update(input: Input): Input

    fun create(input: Input): Input

    fun findByName(inputName: String): List<Input>
}
