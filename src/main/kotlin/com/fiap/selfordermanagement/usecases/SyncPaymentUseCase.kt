package com.fiap.selfordermanagement.usecases

interface SyncPaymentUseCase {

    fun syncPayment(orderNumber: Long, externalOrderGlobalId: String)
}
