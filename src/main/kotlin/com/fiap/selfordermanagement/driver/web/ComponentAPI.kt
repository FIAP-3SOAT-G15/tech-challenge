package com.fiap.selfordermanagement.driver.web

import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.driver.web.request.ComponentRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "composição", description = "API de componentes")
@RequestMapping("/admin/components")
interface ComponentAPI {
    @Operation(
        summary = "Retorna todos os componentes",
        parameters = [
            Parameter(
                name = "x-admin-token",
                required = true,
                `in` = ParameterIn.HEADER,
                schema = Schema(type = "string", defaultValue = "token"),
            ),
        ],
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<Component>>

    @Operation(
        summary = "Retorna componentes do produto",
        parameters = [
            Parameter(
                name = "x-admin-token",
                required = true,
                `in` = ParameterIn.HEADER,
                schema = Schema(type = "string", defaultValue = "token"),
            ),
        ],
    )
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

    @Operation(
        summary = "Cadastra componente",
        parameters = [
            Parameter(
                name = "x-admin-token",
                required = true,
                `in` = ParameterIn.HEADER,
                schema = Schema(type = "string", defaultValue = "token"),
            ),
        ],
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "422", description = "Quantidade inválida"),
        ],
    )
    @PostMapping
    fun create(
        @Parameter(description = "Cadastro de componente") @RequestBody componentRequest: ComponentRequest,
    ): ResponseEntity<Component>

    @Operation(
        summary = "Pesquisa componente por nome",
        parameters = [
            Parameter(
                name = "x-admin-token",
                required = true,
                `in` = ParameterIn.HEADER,
                schema = Schema(type = "string", defaultValue = "token"),
            ),
        ],
    )
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
