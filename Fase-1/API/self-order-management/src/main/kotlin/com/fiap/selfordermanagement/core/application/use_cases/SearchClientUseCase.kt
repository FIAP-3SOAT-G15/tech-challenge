package com.fiap.selfordermanagement.core.application.use_cases

import com.fiap.selfordermanagement.core.domain.entities.Client

interface SearchClientUseCase {

    fun execute(document: String): Client?
}