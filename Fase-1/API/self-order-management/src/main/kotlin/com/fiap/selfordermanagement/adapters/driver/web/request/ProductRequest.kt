package com.fiap.selfordermanagement.adapters.driver.web.request

import com.fiap.selfordermanagement.application.domain.entities.Product
import com.fiap.selfordermanagement.application.domain.valueobjects.ProductType
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

data class ProductRequest(
    @Schema(title = "Número", example = "123", required = true)
    val number: Long,
    @Schema(title = "Nome", example = "Big Mac", required = true)
    val name: String,
    @Schema(title = "Tipo", example = "MAIN", required = true)
    val type: String,
    @Schema(title = "Preço", example = "10.00", required = true)
    val price: BigDecimal,
    @Schema(
        title = "Descrição",
        example = "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, num pão com gergelim",
        required = true,
    )
    val description: String,
    @Schema(title = "Categoria", example = "Hambúrguer", required = true)
    val category: String,
    @Schema(title = "Número mínimo de subitens", description = "", example = "1", minimum = "0", required = true)
    val minSub: Int = 0,
    @Schema(title = "Número máximo de subitens", description = "", example = "3", minimum = "0", required = true)
    val maxSub: Int = Int.MAX_VALUE,
    @Schema(title = "Itens de estoque", type = "array", example = "[\"1\", \"2\", \"3\"]", minLength = 1, required = true)
    val inputs: List<Int>,
) {
    fun toDomain(): Product {
        return Product(
            number = number,
            name = name,
            type = ProductType.valueOf(type),
            price = price,
            description = description,
            category = category,
            minSub = minSub,
            maxSub = maxSub,
            subItem = arrayListOf(),
            inputs = arrayListOf(),
        )
    }
}
