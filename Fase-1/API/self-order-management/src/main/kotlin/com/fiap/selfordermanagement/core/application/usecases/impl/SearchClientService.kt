package com.fiap.selfordermanagement.core.application.usecases.impl

import com.fiap.selfordermanagement.core.application.usecases.SearchClientUseCase
import com.fiap.selfordermanagement.core.domain.entities.Client
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository

class SearchClientService(private val repository: ClientRepository) : SearchClientUseCase {
    override fun execute(document: String): Client? {
        return repository.findByDocument(document)
    }
}
