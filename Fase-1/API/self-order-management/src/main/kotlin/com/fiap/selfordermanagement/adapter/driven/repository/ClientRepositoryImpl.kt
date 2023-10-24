package com.fiap.selfordermanagement.adapter.driven.repository

import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ClientJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.mapper.ClientMapper
import com.fiap.selfordermanagement.core.domain.entities.Client
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
import org.mapstruct.factory.Mappers

class ClientRepositoryImpl(private val clientJpaRepository: ClientJpaRepository) : ClientRepository {
    private val mapper: ClientMapper = Mappers.getMapper(ClientMapper::class.java)

    override fun findByDocument(document: String): Client? {
        return clientJpaRepository.findById(document)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun getAll(): List<Client> {
        return clientJpaRepository.findAll().map { mapper.toDomain(it) }
    }

    override fun upsert(client: Client): Client {
        val newClient = findByDocument(client.document)?.let { it.update(client) } ?: client
        return newClient
            .let(mapper::toEntity)
            .let(clientJpaRepository::save)
            .let(mapper::toDomain)
    }
}
