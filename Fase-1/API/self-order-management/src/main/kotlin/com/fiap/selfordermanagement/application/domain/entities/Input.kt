package com.fiap.selfordermanagement.application.domain.entities

data class Input(val number: Long? = null, val name: String, val stock: Stock) {
    fun update(input: Input): Input {
        return Input(number = number, name = input.name, stock = input.stock)
    }
}
