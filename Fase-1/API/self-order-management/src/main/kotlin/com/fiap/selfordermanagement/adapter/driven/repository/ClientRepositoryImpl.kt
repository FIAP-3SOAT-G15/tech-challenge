package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.core.domain.entities.Client
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
import java.util.Collections

class ClientRepositoryImpl : ClientRepository {
    override fun findByName(name: String): Client? {
        return null;
    }

    override fun findAll(): List<Client> {
        return Collections.emptyList();
    }

    override fun findByDocument(document: String): Client? {
        return null
    }
}