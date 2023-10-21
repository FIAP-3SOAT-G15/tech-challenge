package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Client

interface ClientRepository {
    fun findByDocument(document: String) : Client?
    fun getAll() : List<Client>

    fun upsert(client: Client) : Client

}