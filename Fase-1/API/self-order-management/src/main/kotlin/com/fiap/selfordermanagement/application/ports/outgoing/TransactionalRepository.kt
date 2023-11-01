package com.fiap.selfordermanagement.application.ports.outgoing

interface TransactionalRepository {
    fun <T> transaction(code: () -> T): T
}
