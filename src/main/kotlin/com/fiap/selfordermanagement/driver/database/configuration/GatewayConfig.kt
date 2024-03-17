package com.fiap.selfordermanagement.driver.database.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapter.gateway.ComponentGateway
import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.adapter.gateway.OrderGateway
import com.fiap.selfordermanagement.adapter.gateway.PaymentGateway
import com.fiap.selfordermanagement.adapter.gateway.ProductGateway
import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.adapter.gateway.TransactionalGateway
import com.fiap.selfordermanagement.adapter.gateway.impl.ComponentGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.CustomerGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.OrderGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.PaymentGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.ProductGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.StockGatewayImpl
import com.fiap.selfordermanagement.adapter.gateway.impl.TransactionalGatewayImpl
import com.fiap.selfordermanagement.driver.database.persistence.jpa.ComponentJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.jpa.PaymentJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.jpa.ProductJpaRepository
import com.fiap.selfordermanagement.driver.database.persistence.jpa.StockJpaRepository
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
