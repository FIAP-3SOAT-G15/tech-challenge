package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.ComponentRequest
import com.fiap.selfordermanagement.application.domain.entities.Component
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "composição", description = "API de componentes")
@RequestMapping("/admin/components")
interface ComponentAPI {
    @Operation(summary = "Retorna todos os componentes")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<Component>>

    @Operation(summary = "Retorna componentes do produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        ],
    )
    @GetMapping("/{productNumber}")
    fun findByProductNumber(
        @Parameter(description = "Número do produto") @PathVariable productNumber: Long,
    ): ResponseEntity<List<Component>>

    @Operation(summary = "Cadastra componente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "422", description = "Quantidade inválida"),
        ],
    )
    @PostMapping(consumes = ["application/json"])
    fun create(
        @Parameter(description = "Cadastro de componente") @RequestBody componentRequest: ComponentRequest,
    ): ResponseEntity<Component>

    @Operation(summary = "Pesquisa componente por nome")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping("/search")
    fun searchByName(
        @Parameter(description = "Nome do componente") @RequestParam("name") name: String,
    ): ResponseEntity<List<Component>>
}
