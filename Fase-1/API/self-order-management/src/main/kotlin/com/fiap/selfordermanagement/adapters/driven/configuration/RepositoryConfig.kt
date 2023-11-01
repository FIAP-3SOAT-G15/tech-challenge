package com.fiap.selfordermanagement.adapters.driven.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapters.driven.persistence.ComponentRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.CustomerRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.OrderRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.PaymentRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.ProductRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.StockRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.TransactionalRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.ComponentJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.PaymentJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.ProductJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.StockJpaRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ComponentRepository
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import com.fiap.selfordermanagement.application.ports.outgoing.PaymentRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ProductRepository
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository
import com.fiap.selfordermanagement.application.ports.outgoing.TransactionalRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class RepositoryConfig {
    @Bean("CustomerRepository")
    fun createCustomerRepository(customerJpaRepository: CustomerJpaRepository): CustomerRepository {
        return CustomerRepositoryImpl(customerJpaRepository)
    }

    @Bean("ComponentRepository")
    fun createComponentRepository(componentJpaRepository: ComponentJpaRepository): ComponentRepository {
        return ComponentRepositoryImpl(componentJpaRepository)
    }

    @Bean("StockRepository")
    fun createStockRepository(stockJpaRepository: StockJpaRepository): StockRepository {
        return StockRepositoryImpl(stockJpaRepository)
    }

    @Bean("ProductRepository")
    fun createProductRepository(productJpaRepository: ProductJpaRepository): ProductRepository {
        return ProductRepositoryImpl(productJpaRepository)
    }

    @Bean("OrderRepository")
    fun createOrderRepository(orderJpaRepository: OrderJpaRepository): OrderRepository {
        return OrderRepositoryImpl(orderJpaRepository)
    }

    @Bean("PaymentRepository")
    fun createPaymentRepository(paymentJpaRepository: PaymentJpaRepository): PaymentRepository {
        return PaymentRepositoryImpl(paymentJpaRepository)
    }

    @Bean("TransactionalRepository")
    fun createTransactionalRepository(): TransactionalRepository {
        return TransactionalRepositoryImpl()
    }
}
