package com.fiap.selfordermanagement.adapters.driver.web.api

import com.fiap.selfordermanagement.adapters.driver.web.request.ProductComposeRequest
import com.fiap.selfordermanagement.adapters.driver.web.request.ProductRequest
import com.fiap.selfordermanagement.adapters.driver.web.response.ProductResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "produto", description = "API de produtos")
@RequestMapping("/admin/products")
interface ProductAPI {
    @Operation(summary = "Retorna todos os produtos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<ProductResponse>>

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
    ): ResponseEntity<List<ProductResponse>>

    @Operation(summary = "Retorna produto pelo número")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        ],
    )
    @GetMapping("/{productNumber}")
    fun getByProductNumber(
        @Parameter(description = "Número do produto") @PathVariable("productNumber") productNumber: Long,
    ): ResponseEntity<ProductResponse>

    @Operation(summary = "Pesquisa produto por nome")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping("/search")
    fun searchByName(
        @Parameter(description = "Nome do produto") @PathParam("name") name: String,
    ): ResponseEntity<List<ProductResponse>>

    @Operation(summary = "Cadastra produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "422", description = "Produto inválido"),
        ],
    )
    @PostMapping(consumes = ["application/json"])
    fun create(
        @Parameter(description = "Cadastro do produto") @RequestBody productRequest: ProductRequest,
    ): ResponseEntity<ProductResponse>

    @Operation(summary = "Atualiza produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            ApiResponse(responseCode = "422", description = "Produto inválido"),
        ],
    )
    @PutMapping("/{productNumber}", consumes = ["application/json"])
    fun update(
        @Parameter(description = "Número do produto") @PathVariable productNumber: Long,
        @Parameter(description = "Cadastro do produto") @RequestBody productRequest: ProductRequest,
    ): ResponseEntity<ProductResponse>

    @Operation(summary = "Remove produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        ],
    )
    @DeleteMapping("/{productNumber}")
    fun delete(
        @Parameter(description = "Número do produto") @PathVariable("productNumber") productNumber: Long,
    ): ResponseEntity<ProductResponse>

    @Operation(summary = "Atribui subitems ao produto")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        ],
    )
    @PostMapping("/compose", consumes = ["application/json"])
    fun compose(
        @Parameter(description = "Montagem do produto") @RequestBody productComposeRequest: ProductComposeRequest,
    ): ResponseEntity<ProductResponse>
}
