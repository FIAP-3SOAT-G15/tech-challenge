package com.fiap.selfordermanagement.core.domain.entities

import com.fiap.selfordermanagement.core.domain.value_objects.Status
import java.math.BigDecimal

data class Order(
    val id: Long? = null,
    val total: BigDecimal,
    val nickName: String,
    val status: Status = Status.CREATED,
    val client: Client? = null,
    val items: List<Item> = emptyList(),
)