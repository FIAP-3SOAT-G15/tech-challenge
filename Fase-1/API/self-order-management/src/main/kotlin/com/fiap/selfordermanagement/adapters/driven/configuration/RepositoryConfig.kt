package com.fiap.selfordermanagement.adapters.driven.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.adapters.driven.persistence.*
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.*
import com.fiap.selfordermanagement.application.ports.outgoing.*
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

    @Bean("ProductRepository")
    fun createProductRepository(productJpaRepository: ProductJpaRepository): ProductRepository {
        return ProductRepositoryImpl(productJpaRepository)
    }

    @Bean("StockRepository")
    fun createStockRepository(stockJpaRepository: StockJpaRepository): StockRepository {
        return StockRepositoryImpl(stockJpaRepository)
    }

    @Bean("OrderRepository")
    fun createOrderRepository(orderJpaRepository: OrderJpaRepository): OrderRepository {
        return OrderRepositoryImpl(orderJpaRepository)
    }

    @Bean("PaymentRepository")
    fun createPaymentRepository(paymentJpaRepository: PaymentJpaRepository): PaymentRepository {
        return PaymentRepositoryImpl(paymentJpaRepository)
    }

    @Bean("InputRepository")
    fun createInputRepository(inputJpaRepository: InputJpaRepository): InputRepository {
        return InputRepositoryImpl(inputJpaRepository)
    }
}
