package com.fiap.selfordermanagement.driver.database.persistence.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
class PaymentEntity(
    @Id
    @Column(name = "payment_order_number")
    val orderNumber: Long,
    @Column(name = "payment_external_order_id")
    val externalOrderId: String,
    @Column(name = "payment_payment_info")
    val paymentInfo: String,
    @Column(name = "payment_created_at")
    val createdAt: LocalDateTime,
    @Column(name = "payment_status")
    val status: String,
    @Column(name = "payment_status_changed_at")
    val statusChangedAt: LocalDateTime,
)
