package com.fiap.selfordermanagement.driver.database.persistence.entities

import com.fiap.selfordermanagement.domain.valueobjects.PaymentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    @Column(name = "payment_external_order_global_id")
    val externalOrderGlobalId: String?,
    @Column(name = "payment_payment_info")
    val paymentInfo: String,
    @Column(name = "payment_created_at")
    val createdAt: LocalDateTime,
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    val status: PaymentStatus,
    @Column(name = "payment_status_changed_at")
    val statusChangedAt: LocalDateTime,
)
