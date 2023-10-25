package com.fiap.selfordermanagement.adapters.driven.configuration

import com.fiap.selfordermanagement.adapters.driven.persistence.CustomerRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.ItemRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.OrderRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.StockRepositoryImpl
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.CustomerJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapters.driven.persistence.jpa.StockJpaRepository
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import com.fiap.selfordermanagement.application.ports.outgoing.ItemRepository
import com.fiap.selfordermanagement.application.ports.outgoing.OrderRepository
import com.fiap.selfordermanagement.application.ports.outgoing.StockRepository
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
