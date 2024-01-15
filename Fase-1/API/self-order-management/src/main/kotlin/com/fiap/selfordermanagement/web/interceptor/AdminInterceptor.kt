package com.fiap.selfordermanagement.web.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AdminInterceptor(private val environment: Environment) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        return request.requestURI?.let {
            if (it.startsWith("/admin")) {
                val adminToken = environment.getProperty("self-order.admin.access-token")
                val adminTokenOnline = request.getHeader(ADMIN_TOKEN_HEADER)
                if (!adminToken.equals(adminTokenOnline)) {
                    response.writer.write("Only administrator access allowed")
                    response.status = HttpStatus.FORBIDDEN.value()
                    return false
                }
            }
            return true
        } ?: true
    }

    companion object {
        const val ADMIN_TOKEN_HEADER = "x-admin-token"
    }
}
