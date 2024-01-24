import com.fiap.selfordermanagement.domain.valueobjects.ProductCategory
import com.fiap.selfordermanagement.driver.web.request.ComponentRequest
import com.fiap.selfordermanagement.driver.web.request.CustomerRequest
import com.fiap.selfordermanagement.driver.web.request.ProductRequest
import java.math.BigDecimal

fun createCustomerRequest(
    document: String = "444.555.666-77",
    name: String = "Fulano de Tal",
    email: String = "fulano@detal.com",
    phone: String = "5511999999999",
    address: String = "São Paulo",
) = CustomerRequest(
    document = document,
    name = name,
    email = email,
    phone = phone,
    address = address,
)

fun createProductRequest(
    name: String = "Big Mac",
    category: String = ProductCategory.MAIN.name,
    price: BigDecimal = BigDecimal("10.00"),
    description: String = "Dois hambúrgueres, alface, queijo, molho especial, cebola, picles, num pão com gergelim",
    minSub: Int = 3,
    maxSub: Int = 3,
    components: List<Long> = listOf(1, 2, 3, 4, 5, 6, 7),
): ProductRequest {
    return ProductRequest(
        name = name,
        category = category,
        price = price,
        description = description,
        minSub = minSub,
        maxSub = maxSub,
        components = components,
    )
}

fun createNewInputRequests(): List<ComponentRequest> {
    return listOf(
        ComponentRequest("Hambúrguer", 100),
        ComponentRequest("Alface", 100),
        ComponentRequest("Queijo", 100),
        ComponentRequest("Molho especial", 100),
        ComponentRequest("Cebola", 100),
        ComponentRequest("Picles", 100),
        ComponentRequest("Pão com gergelim", 100),
    )
}
