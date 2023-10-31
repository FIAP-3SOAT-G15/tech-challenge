package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.CustomerRequest
import com.fiap.selfordermanagement.application.domain.entities.Customer
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "cliente", description = "API de clientes")
@RequestMapping("/customers")
interface CustomersAPI {
    @Operation(summary = "Retorna todos os clientes")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<Customer>>

    @Operation(summary = "Retorna cliente pelo documento")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        ],
    )
    @GetMapping("/{document}")
    fun getByDocument(
        @Parameter(description = "Documento do cliente") @PathVariable("document") document: String,
    ): ResponseEntity<Customer?>

    @Operation(summary = "Pesquisa clientes por nome")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping("/search")
    fun searchByName(
        @Parameter(description = "Nome do cliente") @RequestParam("name") name: String,
    ): ResponseEntity<List<Customer>>

    @Operation(summary = "Cadastra cliente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "422", description = "Cadastro inválido"),
        ],
    )
    @PostMapping(consumes = ["application/json"])
    fun create(
        @Parameter(description = "Cadastro de cliente") @RequestBody customerRequest: CustomerRequest,
    ): ResponseEntity<Customer>

    @Operation(summary = "Atualiza cadastro de cliente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        ],
    )
    @PutMapping("/{document}", consumes = ["application/json"])
    fun update(
        @Parameter(description = "Documento do cliente") @PathVariable("document") document: String,
        @Parameter(description = "Cadastro de cliente") @RequestBody customerRequest: CustomerRequest,
    ): ResponseEntity<Customer>

    @Operation(summary = "Remove cadastro de cliente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            ApiResponse(responseCode = "402", description = "Pagamento necessário"),
        ],
    )
    @DeleteMapping("/{document}")
    fun remove(
        @Parameter(description = "Documento do cliente") @PathVariable("document") document: String,
    ): ResponseEntity<Customer>
}
