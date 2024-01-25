package com.fiap.selfordermanagement.driver.database.persistence.jpa

import com.fiap.selfordermanagement.driver.database.persistence.entities.PaymentEntity
import org.springframework.data.repository.CrudRepository

interface PaymentJpaRepository : CrudRepository<PaymentEntity, Long>
