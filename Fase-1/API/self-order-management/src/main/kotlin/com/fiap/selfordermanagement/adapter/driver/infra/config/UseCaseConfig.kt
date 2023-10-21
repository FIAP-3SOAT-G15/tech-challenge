package com.fiap.selfordermanagement.adapter.driver.infra.config

import com.fiap.selfordermanagement.core.application.use_cases.CompleteOrderUseCase
import com.fiap.selfordermanagement.core.application.use_cases.DeleteItemsUseCase
import com.fiap.selfordermanagement.core.application.use_cases.GetAllClientUseCase
import com.fiap.selfordermanagement.core.application.use_cases.SearchClientUseCase
import com.fiap.selfordermanagement.core.application.use_cases.impl.CompleteOrderService
import com.fiap.selfordermanagement.core.application.use_cases.impl.DeleteItemsService
import com.fiap.selfordermanagement.core.application.use_cases.impl.GetAllClientService
import com.fiap.selfordermanagement.core.application.use_cases.impl.SearchClientService
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
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
}