package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.NewInputRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.QuantityRequest
import com.fiap.selfordermanagement.application.domain.entities.Input
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

@Tag(name = "estoque", description = "API de items em estoque")
@RequestMapping("/admin/stock")
interface StockAPI {
    @Operation(summary = "Retorna todos os itens em estoque")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping(consumes = ["application/json"])
    fun findAll(): ResponseEntity<List<Input>>

    @Operation(summary = "Retorna itens em estoque para produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Item de estoque não encontrado"),
        ],
    )
    @GetMapping("/{productNumber}", consumes = ["application/json"])
    fun getByProductNumber(
        @Parameter(description = "Número do produto") @PathVariable productNumber: Long,
    ): ResponseEntity<List<Input>>

    @Operation(summary = "Acrescenta quantidade do item em estoque")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Item de estoque não encontrado"),
            ApiResponse(responseCode = "422", description = "Quantidade inválida"),
        ],
    )
    @PostMapping("/{inputNumber}/increment", consumes = ["application/json"])
    fun increment(
        @Parameter(description = "Número do item em estoque") @PathVariable inputNumber: Long,
        @Parameter(description = "Quantidade a acrescentar") @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Input>

    @Operation(summary = "Reduz quantidade do item em estoque")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Item de estoque não encontrado"),
            ApiResponse(responseCode = "422", description = "Quantidade inválida"),
        ],
    )
    @PostMapping("/{inputNumber}/decrement", consumes = ["application/json"])
    fun decrement(
        @Parameter(description = "Número do item em estoque") @PathVariable inputNumber: Long,
        @Parameter(description = "Quantidade a reduzir") @RequestBody quantityRequest: QuantityRequest,
    ): ResponseEntity<Input>

    @Operation(summary = "Cadastra item em estoque")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "422", description = "Quantidade inválida"),
        ],
    )
    @PostMapping(consumes = ["application/json"])
    fun create(
        @Parameter(description = "Cadastro de item em estoque") @RequestBody newInputRequest: NewInputRequest,
    ): ResponseEntity<Input>

    @Operation(summary = "Pesquisa item em estoque por nome")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping("/search", consumes = ["application/json"])
    fun searchByName(
        @Parameter(description = "Nome do item em estoque") @RequestParam("name") name: String,
    ): ResponseEntity<List<Input>>
}
