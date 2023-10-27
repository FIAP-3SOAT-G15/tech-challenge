package com.fiap.selfordermanagement.adapters.driven.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapters.driven.provider.MercadoPagoPaymentProvider
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentProvider
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
