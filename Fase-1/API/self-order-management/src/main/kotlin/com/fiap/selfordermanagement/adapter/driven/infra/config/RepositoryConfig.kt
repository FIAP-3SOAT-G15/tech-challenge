package com.fiap.selfordermanagement.adapter.driven.infra.config

import com.fiap.selfordermanagement.adapter.driven.repository.ClientRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.ItemRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.OrderRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.StockRepositoryImpl
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ClientJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.ItemJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.OrderJpaRepository
import com.fiap.selfordermanagement.adapter.driven.repository.jpa.StockJpaRepository
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
import com.fiap.selfordermanagement.core.domain.repositories.ItemRepository
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository
import com.fiap.selfordermanagement.core.domain.repositories.StockRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean("ClientRepository")
    fun createClientRepository(clientJpaRepository: ClientJpaRepository): ClientRepository {
        return ClientRepositoryImpl(clientJpaRepository)
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