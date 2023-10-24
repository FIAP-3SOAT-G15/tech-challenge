package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository

interface CustomerJpaRepository : CrudRepository<CustomerEntity, String>
