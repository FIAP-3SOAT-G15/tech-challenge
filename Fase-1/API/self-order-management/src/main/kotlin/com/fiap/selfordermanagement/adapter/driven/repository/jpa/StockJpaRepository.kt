package com.fiap.selfordermanagement.adapter.driven.repository.jpa

import com.fiap.selfordermanagement.adapter.driven.repository.entities.StockEntity
import org.springframework.data.repository.CrudRepository

interface StockJpaRepository : CrudRepository<StockEntity, String>