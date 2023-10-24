package com.fiap.selfordermanagement.adapter.driver.web

import com.fiap.selfordermanagement.adapter.driver.web.api.ClientApi
import com.fiap.selfordermanagement.core.application.use_cases.GetAllClientUseCase
import com.fiap.selfordermanagement.core.application.use_cases.SearchClientUseCase
import com.fiap.selfordermanagement.core.domain.entities.Client
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientController(
    private val getAllClientUseCase: GetAllClientUseCase,
    private val searchClientUseCase: SearchClientUseCase
) : ClientApi {

    override fun getAllClient(): List<Client> {
        return getAllClientUseCase.execute();
    }

    override fun searchClient(document: String): Client? {
        return searchClientUseCase.execute(document)
    }
}