package com.fiap.selfordermanagement.application.usecases

import com.fiap.selfordermanagement.application.domain.entities.Payment

interface SyncPaymentStatusUseCase {
    fun syncPaymentStatus(payment: Payment): Payment
}
