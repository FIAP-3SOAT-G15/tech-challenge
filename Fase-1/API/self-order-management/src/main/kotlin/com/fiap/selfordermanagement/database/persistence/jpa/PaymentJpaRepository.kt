package com.fiap.selfordermanagement.database.persistence.jpa

import com.fiap.selfordermanagement.database.persistence.entities.PaymentEntity
import org.springframework.data.repository.CrudRepository

interface PaymentJpaRepository : CrudRepository<PaymentEntity, Long>
