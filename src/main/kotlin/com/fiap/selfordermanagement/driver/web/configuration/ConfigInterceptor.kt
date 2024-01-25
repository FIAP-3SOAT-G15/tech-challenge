package com.fiap.selfordermanagement.driver.web.configuration

import com.fiap.selfordermanagement.driver.web.interceptor.AdminInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ConfigInterceptor(private val adminInterceptor: AdminInterceptor) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(adminInterceptor)
    }
}