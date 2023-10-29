package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Input

interface AdjustInventoryUseCase {
    fun increment(
        inputNumber: Long,
        quantity: Long,
    ): Input

    fun decrement(
        inputNumber: Long,
        quantity: Long,
    ): Input

    fun createInput(input: Input): Input

    fun findInput(name: String): List<Input>
}
