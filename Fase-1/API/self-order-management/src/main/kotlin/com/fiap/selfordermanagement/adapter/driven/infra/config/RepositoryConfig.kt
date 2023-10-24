package com.fiap.selfordermanagement.adapter.driven.infra.config

import com.fiap.selfordermanagement.adapter.driven.repository.CustomerRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.ItemRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.OrderRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.StockRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.StockJpaRepository
import com.fiap.selfordermanagement.core.domain.repositories.CustomerRepository
import com.fiap.selfordermanagement.core.domain.repositories.ItemRepository
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository
import com.fiap.selfordermanagement.core.domain.repositories.StockRepository
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
