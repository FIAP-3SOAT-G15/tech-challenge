package com.fiap.selfordermanagement.usecases

import com.fiap.selfordermanagement.domain.entities.Payment

interface SyncPaymentStatusUseCase {
    fun syncPaymentStatus(payment: Payment): Payment
}
