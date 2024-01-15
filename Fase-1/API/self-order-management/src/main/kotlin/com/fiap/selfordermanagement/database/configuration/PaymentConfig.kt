package com.fiap.selfordermanagement.database.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.adapter.repository.PaymentProvider
import com.fiap.selfordermanagement.database.provider.MercadoPagoPaymentProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class PaymentConfig {
    @Bean("PaymentProvider")
    fun createPaymentProvider(): PaymentProvider {
        return MercadoPagoPaymentProvider()
    }
}
