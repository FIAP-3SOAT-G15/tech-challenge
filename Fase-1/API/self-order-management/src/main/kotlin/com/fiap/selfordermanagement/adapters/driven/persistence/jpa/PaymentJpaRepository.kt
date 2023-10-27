package com.fiap.selfordermanagement.adapters.driven.persistence.jpa

import com.fiap.selfordermanagement.adapters.driven.persistence.entities.PaymentEntity
import org.springframework.data.repository.CrudRepository

interface PaymentJpaRepository : CrudRepository<PaymentEntity, Long>
