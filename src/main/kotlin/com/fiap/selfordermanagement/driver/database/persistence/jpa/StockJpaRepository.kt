package com.fiap.selfordermanagement.driver.database.persistence.jpa

import com.fiap.selfordermanagement.driver.database.persistence.entities.StockEntity
import org.springframework.data.repository.CrudRepository

interface StockJpaRepository : CrudRepository<StockEntity, Long>
