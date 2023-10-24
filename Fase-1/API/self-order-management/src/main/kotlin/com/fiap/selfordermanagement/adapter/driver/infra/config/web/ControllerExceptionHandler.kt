package com.fiap.selfordermanagement.adapter.driver.infra.config.web

import com.fiap.selfordermanagement.core.domain.errors.ErrorType
import com.fiap.selfordermanagement.core.domain.errors.SelfOrderManagementException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(SelfOrderManagementException::class)
    protected fun domainErrorHandler(domainException: SelfOrderManagementException): ResponseEntity<ApiError> {
        val apiError: ApiError = when (domainException.errorType) {
            ErrorType.ITEM_ALREADY_EXISTS -> ApiError(
                domainException.errorType.name,
                domainException.message,
                422
            )
            else -> ApiError(ErrorType.UNEXPECT_FAILED.name, domainException.localizedMessage, 500)
        }
        return ResponseEntity.status(apiError.status).body(apiError)
    }
}