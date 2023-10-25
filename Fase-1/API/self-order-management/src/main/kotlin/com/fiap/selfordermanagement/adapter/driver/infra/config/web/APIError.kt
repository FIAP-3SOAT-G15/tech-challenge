package com.fiap.selfordermanagement.adapter.driver.infra.config.web

import org.springframework.http.HttpStatus

data class APIError(val error: String, val message: String?, val status: HttpStatus)
