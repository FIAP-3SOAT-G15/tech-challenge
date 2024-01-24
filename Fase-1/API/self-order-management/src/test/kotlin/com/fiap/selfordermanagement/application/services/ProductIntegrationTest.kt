package com.fiap.selfordermanagement.application.services

import IntegrationTest
import WithPostgreSQL
import com.fiap.selfordermanagement.adapter.gateway.ComponentGateway
import com.fiap.selfordermanagement.adapter.gateway.ProductGateway
import com.fiap.selfordermanagement.adapter.gateway.StockGateway
import com.fiap.selfordermanagement.domain.entities.Component
import com.fiap.selfordermanagement.domain.entities.Stock
import com.fiap.selfordermanagement.domain.errors.ErrorType
import com.fiap.selfordermanagement.driver.web.interceptor.AdminInterceptor.Companion.ADMIN_TOKEN_HEADER
import createNewInputRequests
import createProductRequest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest
@WithPostgreSQL
class ProductIntegrationTest {
    @LocalServerPort
    private val port: Int? = null

    @Value("\${self-order.admin.access-token}")
    private lateinit var adminAccessToken: String

    @Autowired
    private lateinit var productRepository: ProductGateway

    @Autowired
    private lateinit var componentRepository: ComponentGateway

    @Autowired
    private lateinit var stockRepository: StockGateway

    @BeforeEach
    fun setUp() {
        productRepository.deleteAll()
        componentRepository.deleteAll()

        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `should succeed to manage and search products in the happy path`() {
        val productRequest =
            createProductRequest(
                name = "Big Mac",
                components = persistComponentsAndStocks(),
            )

        // create
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .body(productRequest)
            .`when`()
            .post("/admin/products")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "number", equalTo(productRepository.findAll()[0].number!!.toInt()),
                "name", equalTo(productRequest.name),
                "price", equalTo(productRequest.price.toString()),
                "description", equalTo(productRequest.description),
                "category", equalTo(productRequest.category),
                "minSub", equalTo(productRequest.minSub),
                "maxSub", equalTo(productRequest.maxSub),
                "subItems", hasSize<Int>(0),
                "components", hasSize<Int>(7),
            )

        val product = productRepository.findAll()[0]

        // list all
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .`when`()
            .get("/admin/products")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                ".",
                hasSize<Int>(1),
                "[0].number",
                equalTo(product.number!!.toInt()),
            )

        // find by category
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .`when`()
            .get("/admin/products/category/${product.category}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                ".",
                hasSize<Int>(1),
                "[0].number",
                equalTo(product.number!!.toInt()),
            )

        // get
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .`when`()
            .get("/admin/products/${product.number}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "number",
                equalTo(product.number!!.toInt()),
            )

        // search
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .param("name", " big ")
            .`when`()
            .get("/admin/products/search")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                ".",
                hasSize<Int>(1),
                "[0].number",
                equalTo(product.number!!.toInt()),
            )

        // update
        val changed = productRequest.copy(description = "New description")
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .body(changed)
            .`when`()
            .put("/admin/products/${product.number}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "description",
                equalTo(changed.description),
            )

        assertThat(productRepository.findByProductNumber(product.number!!)?.description).isEqualTo(changed.description)

        // remove
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .`when`()
            .delete("/admin/products/${product.number}")
            .then()
            .statusCode(HttpStatus.OK.value())

        assertThat(productRepository.findAll()).isEmpty()
    }

    @Test
    fun `should handle corner cases accordingly`() {
        val productRequest = createProductRequest()

        // try to create product without component created first
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .body(productRequest)
            .`when`()
            .post("/admin/products")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("error", equalTo(ErrorType.COMPONENT_NOT_FOUND.name))

        val productRequestWithPrerequisites = createProductRequest(components = persistComponentsAndStocks())

        // try to update non-existent
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .body(productRequestWithPrerequisites)
            .`when`()
            .put("/admin/products/42") // non-existent product
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(
                "error",
                equalTo(ErrorType.PRODUCT_NOT_FOUND.name),
            )

        // try to delete non-existent
        given()
            .contentType(ContentType.JSON)
            .header(ADMIN_TOKEN_HEADER, adminAccessToken)
            .`when`()
            .delete("/admin/products/42") // non-existent product
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(
                "error",
                equalTo(ErrorType.PRODUCT_NOT_FOUND.name),
            )
    }

    private fun persistComponentsAndStocks(): List<Long> {
        return createNewInputRequests().map { componentRequest ->
            val savedComponent = componentRepository.create(Component(name = componentRequest.name))
            stockRepository.create(
                Stock(
                    componentNumber = savedComponent.number!!,
                    quantity = componentRequest.initialQuantity,
                ),
            )
            savedComponent
        }.map { it.number!! }
    }
}
