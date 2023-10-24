package com.fiap.selfordermanagement.adapter.driver.infra.config

import com.fiap.selfordermanagement.core.application.usecases.AssembleProductsUseCase
import com.fiap.selfordermanagement.core.application.usecases.CancelOrderUseCase
import com.fiap.selfordermanagement.core.application.usecases.CompleteOrderUseCase
import com.fiap.selfordermanagement.core.application.usecases.DeleteItemsUseCase
import com.fiap.selfordermanagement.core.application.usecases.GetAllClientUseCase
import com.fiap.selfordermanagement.core.application.usecases.ListOrdersUseCase
import com.fiap.selfordermanagement.core.application.usecases.SearchClientUseCase
import com.fiap.selfordermanagement.core.application.usecases.impl.AssembleProductsService
import com.fiap.selfordermanagement.core.application.usecases.impl.CancelOrderService
import com.fiap.selfordermanagement.core.application.usecases.impl.CompleteOrderService
import com.fiap.selfordermanagement.core.application.usecases.impl.DeleteItemsService
import com.fiap.selfordermanagement.core.application.usecases.impl.GetAllClientService
import com.fiap.selfordermanagement.core.application.usecases.impl.ListOrdersService
import com.fiap.selfordermanagement.core.application.usecases.impl.SearchClientService
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
import com.fiap.selfordermanagement.core.domain.repositories.ItemRepository
import com.fiap.selfordermanagement.core.domain.repositories.OrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
class UseCaseConfig {
    @Bean
    @DependsOn("ClientRepository")
    fun createGetAllClientUseCase(repository: ClientRepository): GetAllClientUseCase {
        return GetAllClientService(repository)
    }

    @Bean
    @DependsOn("ClientRepository")
    fun createSearchClientUseCase(repository: ClientRepository): SearchClientUseCase {
        return SearchClientService(repository)
    }

    @Bean
    @DependsOn("OrderRepository")
    fun createCompleteOrderUseCase(repository: OrderRepository): CompleteOrderUseCase {
        return CompleteOrderService(repository)
    }

    @Bean
    @DependsOn("OrderRepository")
    fun createDeleteItemsUseCase(repository: OrderRepository): DeleteItemsUseCase {
        return DeleteItemsService(repository)
    }

    @Bean
    @DependsOn("OrderRepository")
    fun createListOrdersUseCase(repository: OrderRepository): ListOrdersUseCase {
        return ListOrdersService(repository)
    }

    @Bean
    @DependsOn("ItemRepository")
    fun createAssembleProductsUseCase(repository: ItemRepository): AssembleProductsUseCase {
        return AssembleProductsService(repository)
    }

    @Bean
    @DependsOn("OrderRepository")
    fun createCancelOrderUseCase(repository: OrderRepository): CancelOrderUseCase {
        return CancelOrderService(repository)
    }
}
