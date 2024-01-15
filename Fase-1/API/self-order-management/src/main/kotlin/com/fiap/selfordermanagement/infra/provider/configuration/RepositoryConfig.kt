package com.fiap.selfordermanagement.infra.provider.configuration

import com.fiap.selfordermanagement.application.adapter.CustomerRepositoryImpl
import com.fiap.selfordermanagement.application.adapter.repository.CustomerRepository
import com.fiap.selfordermanagement.application.adapter.repository.ItemRepository
import com.fiap.selfordermanagement.application.adapter.repository.OrderRepository
import com.fiap.selfordermanagement.application.adapter.repository.StockRepository
import com.fiap.selfordermanagement.application.adapter.ItemRepositoryImpl
import com.fiap.selfordermanagement.application.adapter.OrderRepositoryImpl
import com.fiap.selfordermanagement.application.adapter.StockRepositoryImpl
import com.fiap.selfordermanagement.infra.provider.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.infra.provider.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.infra.provider.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.infra.provider.jpa.StockJpaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {
    @Bean("CustomerRepository")
    fun createCustomerRepository(customerJpaRepository: CustomerJpaRepository): CustomerRepository {
        return CustomerRepositoryImpl(customerJpaRepository)
    }

    @Bean("OrderRepository")
    fun createOrderRepository(orderJpaRepository: OrderJpaRepository): OrderRepository {
        return OrderRepositoryImpl(orderJpaRepository)
    }

    @Bean("ItemRepository")
    fun createItemRepository(itemJpaRepository: ItemJpaRepository): ItemRepository {
        return ItemRepositoryImpl(itemJpaRepository)
    }

    @Bean("StockRepository")
    fun createStockRepository(stockJpaRepository: StockJpaRepository): StockRepository {
        return StockRepositoryImpl(stockJpaRepository)
    }
}
