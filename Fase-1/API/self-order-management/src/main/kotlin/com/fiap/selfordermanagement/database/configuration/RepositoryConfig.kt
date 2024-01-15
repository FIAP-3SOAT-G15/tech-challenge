package com.fiap.selfordermanagement.database.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.adapter.repository.ComponentRepository
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.application.adapter.repository.PaymentRepository
import com.fiap.selfordermanagement.application.adapter.repository.ProductRepository
import com.fiap.selfordermanagement.application.adapter.repository.StockRepository
import com.fiap.selfordermanagement.application.adapter.repository.TransactionalRepository
import com.fiap.selfordermanagement.database.persistence.*
import com.fiap.selfordermanagement.database.persistence.jpa.*
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
