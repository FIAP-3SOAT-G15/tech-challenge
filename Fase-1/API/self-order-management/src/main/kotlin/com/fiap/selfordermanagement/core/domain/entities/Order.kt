package com.fiap.selfordermanagement.core.domain.entities

import com.fiap.selfordermanagement.core.domain.valueobjects.Status
import java.math.BigDecimal

data class Order(
    val id: Long? = null,
    val total: BigDecimal,
    val nickname: String,
    val status: Status = Status.CREATED,
    val customer: Customer? = null,
    val items: List<Item> = emptyList(),
)
