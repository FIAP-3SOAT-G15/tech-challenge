package com.fiap.selfordermanagement.web.api

import com.fiap.selfordermanagement.application.domain.entities.Payment
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "pagamento", description = "API de pagamentos")
@RequestMapping("/payments")
interface PaymentAPI {
    @Operation(summary = "Retorna todos os pagamentos")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
        ],
    )
    @GetMapping
    fun findAll(): ResponseEntity<List<Payment>>

    @Operation(summary = "Retorna pagamento do pedido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Operação bem-sucedida"),
            ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
        ],
    )
    @GetMapping("/{orderNumber}")
    fun getByOrderNumber(
        @Parameter(description = "Número do pedido") @PathVariable orderNumber: Long,
    ): ResponseEntity<Payment>
}
