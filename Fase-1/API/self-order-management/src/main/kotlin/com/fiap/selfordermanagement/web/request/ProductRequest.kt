package com.fiap.selfordermanagement.web.request

import com.fasterxml.jackson.annotation.JsonFormat
import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class ProductRequest(
    @Schema(title = "Nome do produto", example = "Big Mac", required = true)
    val name: String,
    @Schema(
        title = "Categoria",
        example = "MAIN",
        allowableValues = ["DRINK", "MAIN", "SIDE", "DESSERT"],
        required = true,
    )
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
    @Schema(
        title = "Componentes do produto",
        type = "array",
        example = "[\"1\", \"2\", \"3\"]",
        minLength = 1,
        required = true,
    )
    val components: List<Long>,
) {
    fun toDomain(): Product {
        return Product(
            name = name,
            category = ProductCategory.valueOf(category),
            price = price,
            description = description,
            minSub = minSub,
            maxSub = maxSub,
            subItems = arrayListOf(),
            components = arrayListOf(),
        )
    }
}
