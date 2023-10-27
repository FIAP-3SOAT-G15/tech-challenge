package com.fiap.selfordermanagement.application.ports.incoming

import com.fiap.selfordermanagement.application.domain.entities.Payment

interface SyncPaymentStatusUseCase {
    fun syncPaymentStatus(payment: Payment): Payment
}
