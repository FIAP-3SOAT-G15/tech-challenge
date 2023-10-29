package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Input
import com.fiap.selfordermanagement.application.domain.entities.Stock

data class NewInputRequest(val name: String, val quantity: Long) {
    fun toDomain(): Input {
        return Input(name = name, stock = Stock(quantity = quantity))
    }
}
