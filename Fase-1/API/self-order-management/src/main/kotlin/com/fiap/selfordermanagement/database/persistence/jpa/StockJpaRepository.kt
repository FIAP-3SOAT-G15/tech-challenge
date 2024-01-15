package com.fiap.selfordermanagement.database.persistence.jpa

import com.fiap.selfordermanagement.database.persistence.entities.StockEntity
import org.springframework.data.repository.CrudRepository

interface StockJpaRepository : CrudRepository<StockEntity, Long>
