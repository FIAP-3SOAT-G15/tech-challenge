package com.fiap.selfordermanagement.adapter.driver.infra.config.web

import com.fiap.selfordermanagement.core.domain.errors.ErrorType
import com.fiap.selfordermanagement.core.domain.errors.SelfOrderManagementException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler(SelfOrderManagementException::class)
    protected fun domainErrorHandler(domainException: SelfOrderManagementException): ResponseEntity<APIError> {
        val apiError: APIError =
            when (domainException.errorType) {
                ErrorType.ITEM_ALREADY_EXISTS ->
                    APIError(
                        domainException.errorType.name,
                        domainException.message,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                    )
                else -> APIError(ErrorType.UNEXPECT_FAILED.name, domainException.localizedMessage, HttpStatus.INTERNAL_SERVER_ERROR)
            }
        return ResponseEntity.status(apiError.status).body(apiError)
    }
}
