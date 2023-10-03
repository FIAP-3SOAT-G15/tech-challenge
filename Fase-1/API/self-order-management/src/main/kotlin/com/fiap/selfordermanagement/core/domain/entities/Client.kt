package com.fiap.selfordermanagement.core.domain.entities

import com.fiap.selfordermanagement.core.domain.value_objects.Address

data class Client(val name: String, val address: List<Address>)
