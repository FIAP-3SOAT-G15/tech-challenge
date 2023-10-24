package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.ClientEntity
import org.springframework.data.repository.CrudRepository

interface ClientJpaRepository : CrudRepository<ClientEntity, String>
