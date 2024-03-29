package com.fiap.selfordermanagement.domain.errors

enum class ErrorType {
    CUSTOMER_NOT_FOUND,
    PRODUCT_NOT_FOUND,
    COMPONENT_NOT_FOUND,
    STOCK_NOT_FOUND,
    ORDER_NOT_FOUND,
    PAYMENT_NOT_FOUND,

    PRODUCT_NUMBER_IS_MANDATORY,
    COMPONENT_NUMBER_IS_MANDATORY,

    CUSTOMER_ALREADY_EXISTS,
    PRODUCT_ALREADY_EXISTS,
    STOCK_ALREADY_EXISTS,
    PAYMENT_ALREADY_EXISTS,

    EMPTY_ORDER,
    INSUFFICIENT_STOCK,

    INVALID_PRODUCT_CATEGORY,
    INVALID_ORDER_STATUS,
    INVALID_ORDER_STATE_TRANSITION,

    PAYMENT_NOT_CONFIRMED,
    PAYMENT_REQUEST_NOT_ALLOWED,

    UNEXPECTED_ERROR,
}
