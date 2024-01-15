package com.fiap.selfordermanagement.database.persistence

import com.fiap.selfordermanagement.application.adapter.repository.TransactionalRepository
import org.springframework.transaction.annotation.Transactional

open class TransactionalRepositoryImpl : TransactionalRepository {
    @Transactional
    override fun <T> transaction(code: () -> T): T {
        return code()
    }
}
