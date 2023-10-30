package com.fiap.selfordermanagement.application.domain.valueobjects

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException

enum class OrderStatus {
    CREATED,
    PENDING,
    REJECTED,
    CONFIRMED,
    PREPARING,
    DONE,
    COMPLETED,
    CANCELLED,
    ;

    companion object {
        fun fromString(status: String): OrderStatus {
            return values().firstOrNull { it.name.equals(status.trim(), ignoreCase = true) }
                ?: throw SelfOrderManagementException(
                    errorType = ErrorType.INVALID_ORDER_STATUS,
                    message = "Status $status is not valid",
                )
        }
    }
}
