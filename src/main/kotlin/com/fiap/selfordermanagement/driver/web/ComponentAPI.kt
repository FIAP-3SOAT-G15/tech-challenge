package com.fiap.selfordermanagement.driver.web

import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.driver.web.request.ComponentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    @PostMapping()
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
