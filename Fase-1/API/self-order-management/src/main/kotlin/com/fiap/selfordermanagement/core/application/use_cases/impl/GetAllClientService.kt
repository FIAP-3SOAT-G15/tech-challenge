package com.fiap.selfordermanagement.core.application.use_cases.impl

import com.fiap.selfordermanagement.core.application.use_cases.GetAllClientUseCase
import com.fiap.selfordermanagement.core.domain.entities.Client
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository

class GetAllClientService(private val repository: ClientRepository) : GetAllClientUseCase {

    override fun execute(): List<Client> {
        return repository.findAll()
    }
}