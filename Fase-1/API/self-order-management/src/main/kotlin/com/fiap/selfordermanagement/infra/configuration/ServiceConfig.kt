package com.fiap.selfordermanagement.infra.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.adapter.repository.ItemRepository
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.application.adapter.services.CustomerService
import com.fiap.selfordermanagement.application.adapter.services.OrderService
import com.fiap.selfordermanagement.application.adapter.services.ProductService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [SelfOrderManagementApplication::class])
class ServiceConfig {
    @Bean
    fun createCustomerService(repository: CustomerRepository): CustomerService {
        return CustomerService(repository)
    }

    @Bean
    fun createProductService(repository: ItemRepository): ProductService {
        return ProductService(repository)
    }

    @Bean
    fun createOrderService(repository: OrderRepository): OrderService {
        return OrderService(repository)
    }
}
