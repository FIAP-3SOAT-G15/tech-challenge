package com.fiap.selfordermanagement.adapter.driver.infra.config

import com.fiap.selfordermanagement.core.application.use_cases.GetAllClientUseCase
import com.fiap.selfordermanagement.core.application.use_cases.impl.GetAllClientService
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
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
}