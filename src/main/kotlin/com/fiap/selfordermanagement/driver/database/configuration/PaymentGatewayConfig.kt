package com.fiap.selfordermanagement.driver.database.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapter.gateway.PaymentProviderGateway
import com.fiap.selfordermanagement.client.MercadoPagoClient
import com.fiap.selfordermanagement.driver.database.provider.MercadoPagoPaymentProvider
import com.fiap.selfordermanagement.driver.database.provider.PaymentProviderGatewayMock
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class PaymentGatewayConfig {
    @Bean("PaymentProvider")
    @ConditionalOnProperty("payment-provider.mock", havingValue = "false")
    fun createPaymentProvider(
        mercadoPagoClient: MercadoPagoClient,
        @Value("\${mercadopago.integration.webhookBaseUrl}") webhookBaseUrl: String,
    ): PaymentProviderGateway {
        return MercadoPagoPaymentProvider(
            mercadoPagoClient,
            webhookBaseUrl,
        )
    }
    
    @Bean("PaymentProvider")
    @ConditionalOnProperty("payment-provider.mock", havingValue = "true")
    fun createPaymentProviderMock(): PaymentProviderGateway {
        return PaymentProviderGatewayMock()
    }
}
