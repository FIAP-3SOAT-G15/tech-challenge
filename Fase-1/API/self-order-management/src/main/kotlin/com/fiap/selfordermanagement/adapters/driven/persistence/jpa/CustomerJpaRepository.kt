package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository

interface CustomerJpaRepository : CrudRepository<CustomerEntity, String>
