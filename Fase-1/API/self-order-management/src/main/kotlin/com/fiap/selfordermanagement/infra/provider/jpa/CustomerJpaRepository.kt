package com.fiap.selfordermanagement.infra.provider.jpa

import com.fiap.selfordermanagement.infra.provider.persistence.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository

interface CustomerJpaRepository : CrudRepository<CustomerEntity, String>
