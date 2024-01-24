package com.fiap.selfordermanagement.adapter.gateway

interface TransactionalGateway {
    fun <T> transaction(code: () -> T): T
}
