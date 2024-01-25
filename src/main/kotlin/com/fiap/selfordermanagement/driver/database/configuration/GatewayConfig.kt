package com.fiap.selfordermanagement.driver.database.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapter.gateway.*
import com.fiap.selfordermanagement.adapter.gateway.impl.*
import com.fiap.selfordermanagement.driver.database.persistence.jpa.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class GatewayConfig {
    @Bean("CustomerGateway")
    fun createCustomerGateway(customerJpaRepository: CustomerJpaRepository): CustomerGateway {
        return CustomerGatewayImpl(customerJpaRepository)
    }

    @Bean("ComponentGateway")
    fun createComponentGateway(componentJpaRepository: ComponentJpaRepository): ComponentGateway {
        return ComponentGatewayImpl(componentJpaRepository)
    }

    @Bean("StockGateway")
    fun createStockGateway(stockJpaRepository: StockJpaRepository): StockGateway {
        return StockGatewayImpl(stockJpaRepository)
    }

    @Bean("ProductGateway")
    fun createProductGateway(productJpaRepository: ProductJpaRepository): ProductGateway {
        return ProductGatewayImpl(productJpaRepository)
    }

    @Bean("OrderGateway")
    fun createOrderGateway(orderJpaRepository: OrderJpaRepository): OrderGateway {
        return OrderGatewayImpl(orderJpaRepository)
    }

    @Bean("PaymentGateway")
    fun createPaymentGateway(paymentJpaRepository: PaymentJpaRepository): PaymentGateway {
        return PaymentGatewayImpl(paymentJpaRepository)
    }

    @Bean("TransactionalGateway")
    fun createTransactionalGateway(): TransactionalGateway {
        return TransactionalGatewayImpl()
    }
}
