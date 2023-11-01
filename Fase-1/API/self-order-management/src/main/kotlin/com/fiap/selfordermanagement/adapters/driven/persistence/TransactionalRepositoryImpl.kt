package com.fiap.selfordermanagement.adapters.driven.persistence

import com.fiap.selfordermanagement.application.ports.outgoing.TransactionalRepository
import org.springframework.transaction.annotation.Transactional

open class TransactionalRepositoryImpl : TransactionalRepository {
    @Transactional
    override fun <T> transaction(code: () -> T): T {
        return code()
    }
}
