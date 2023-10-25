package com.fiap.selfordermanagement.adapters.driver.configuration

import com.fiap.selfordermanagement.SelfOrderManagementApplication
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ItemRepository
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import com.fiap.selfordermanagement.application.services.CustomerService
import com.fiap.selfordermanagement.application.services.OrderService
import com.fiap.selfordermanagement.application.services.ProductService
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
