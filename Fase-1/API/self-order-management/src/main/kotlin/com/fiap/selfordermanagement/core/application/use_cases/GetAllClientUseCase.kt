package com.fiap.selfordermanagement.core.application.use_cases

import com.fiap.selfordermanagement.core.domain.entities.Client


interface GetAllClientUseCase {

    fun execute() : List<Client>
}