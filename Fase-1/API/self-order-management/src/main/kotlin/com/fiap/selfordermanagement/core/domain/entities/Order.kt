package com.fiap.selfordermanagement.core.domain.entities

import com.fiap.selfordermanagement.core.domain.value_objects.Status

data class Order(
        val total: Double,
        val nickname: String,
        val status: Status = Status.CREATED,
        val client: Client? = null
)