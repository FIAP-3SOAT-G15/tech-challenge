package com.fiap.selfordermanagement.adapters.driver.web.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fiap.selfordermanagement.application.domain.entities.Component
import com.fiap.selfordermanagement.application.domain.entities.Product
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class ProductResponse(
    @Schema(title = "Número", example = "123", required = true)
    val number: Long,
    @Schema(title = "Nome do produto", example = "Big Mac", required = true)
    val name: String,
    @Schema(title = "Categoria", example = "MAIN", allowableValues = ["DRINK", "MAIN", "SIDE", "DESSERT"], required = true)
    val category: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(title = "Preço", example = "10.00", required = true)
    val price: BigDecimal,
    @Schema(
        title = "Descrição",
        example = "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, num pão com gergelim",
        required = true,
    )
    val description: String,
    @Schema(title = "Número mínimo de subitens", example = "1", minimum = "0", required = true)
    val minSub: Int = 0,
    @Schema(title = "Número máximo de subitens", example = "3", minimum = "0", required = true)
    val maxSub: Int = Int.MAX_VALUE,
    @Schema(title = "Subitens", type = "array", minLength = 1, required = true)
    val subItems: List<Product>,
    @Schema(title = "Componentes", type = "array", minLength = 1, required = true)
    val components: List<Component>,
) {
    companion object {
        fun fromDomain(product: Product): ProductResponse {
            return ProductResponse(
                number = product.number!!,
                name = product.name,
                category = product.category.toString(),
                price = product.price,
                description = product.description,
                minSub = product.minSub,
                maxSub = product.maxSub,
                subItems = product.subItems,
                components = product.components,
            )
        }
    }
}
