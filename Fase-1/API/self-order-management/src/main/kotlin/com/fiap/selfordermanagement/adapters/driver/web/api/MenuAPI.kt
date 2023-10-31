package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.application.domain.entities.Product
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "menu", description = "API de menu")
@RequestMapping("/menu")
interface MenuAPI {
    @Operation(summary = "Retorna todos os produtos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<Product>>

    @Operation(summary = "Retorna produtos por categoria")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "400", description = "Categoria inválida"),
        ],
    )
    @GetMapping("/category/{category}")
    fun findByCategory(
        @Parameter(description = "Categoria") @PathVariable category: String,
    ): ResponseEntity<List<Product>>

    @Operation(summary = "Pesquisa produto por nome")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping("/search")
    fun searchByName(
        @Parameter(description = "Nome do produto") @PathParam("name") name: String,
    ): ResponseEntity<List<Product>>
}
