package com.fiap.selfordermanagement.application.adapter.repository

interface TransactionalRepository {
    fun <T> transaction(code: () -> T): T
}
