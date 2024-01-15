package com.fiap.selfordermanagement.infra.provider.jpa

import com.fiap.selfordermanagement.infra.provider.persistence.entities.StockEntity
import org.springframework.data.repository.CrudRepository

interface StockJpaRepository : CrudRepository<StockEntity, String>
