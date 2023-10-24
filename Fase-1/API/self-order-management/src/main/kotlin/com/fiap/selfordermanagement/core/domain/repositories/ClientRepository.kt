package com.fiap.selfordermanagement.core.domain.repositories

import com.fiap.selfordermanagement.core.domain.entities.Client

interface ClientRepository {
    fun findByName(name: String) : Client?
    fun findAll() : List<Client>
    fun findByDocument(document: String): Client?
}