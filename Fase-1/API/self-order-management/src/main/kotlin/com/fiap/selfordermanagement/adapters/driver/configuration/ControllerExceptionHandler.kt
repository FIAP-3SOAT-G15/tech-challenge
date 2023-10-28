package com.fiap.selfordermanagement.adapters.driver.configuration

import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.domain.errors.SelfOrderManagementException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(SelfOrderManagementException::class)
    protected fun domainErrorHandler(domainException: SelfOrderManagementException): ResponseEntity<ApiError> {
       val apiError: ApiError = when (domainException.errorType) {
           ErrorType.PRODUCT_ALREADY_EXISTS,
           ErrorType.PRODUCT_ALREADY_EXISTS,
           ErrorType.PAYMENT_ALREADY_EXISTS,
           ErrorType.INSUFFICIENT_STOCK -> ApiError(domainException.errorType.name, domainException.message,
               HttpStatus.UNPROCESSABLE_ENTITY.value())

           ErrorType.CUSTOMER_NOT_FOUND,
           ErrorType.PRODUCT_NOT_FOUND,
           ErrorType.STOCK_NOT_FOUND,
           ErrorType.ORDER_NOT_FOUND,
           ErrorType.PAYMENT_NOT_FOUND -> ApiError(domainException.errorType.name, domainException.message,
               HttpStatus.NOT_FOUND.value())

           ErrorType.INVALID_ORDER_STATUS,
           ErrorType.INVALID_ORDER_STATE_TRANSITION,
           ErrorType.EMPTY_ORDER -> ApiError(domainException.errorType.name, domainException.message,
               HttpStatus.BAD_REQUEST.value())

           ErrorType.PAYMENT_NOT_CONFIRMED,
           ErrorType.PAYMENT_REQUEST_NOT_ALLOWED -> ApiError(domainException.errorType.name, domainException.message,
               HttpStatus.PAYMENT_REQUIRED.value())

           else -> ApiError(ErrorType.UNEXPECT_FAILED.name, domainException.localizedMessage,
               HttpStatus.INTERNAL_SERVER_ERROR.value())
       }
        return ResponseEntity.status(apiError.status).body(apiError)
    }

    data class ApiError(val error: String, val message: String?, val status: Int)
}