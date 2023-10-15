package com.fiap.selfordermanagement.adapter.driver.infra.config

import com.fiap.selfordermanagement.adapter.driven.repository.ClientRepositoryImpl
import com.fiap.selfordermanagement.core.domain.repositories.ClientRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {

    @Bean("ClientRepository")
    fun createClientRepository(): ClientRepository {
        return ClientRepositoryImpl()
    }
}