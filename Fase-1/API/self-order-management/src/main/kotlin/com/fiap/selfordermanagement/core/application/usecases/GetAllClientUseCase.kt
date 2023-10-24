package com.fiap.selfordermanagement.core.application.usecases

import com.fiap.selfordermanagement.core.domain.entities.Client

interface GetAllClientUseCase {
    fun execute(): List<Client>
}
