package com.fiap.selfordermanagement.application.services

import IntegrationTest
import WithPostgreSQL
import com.fiap.selfordermanagement.adapter.gateway.CustomerGateway
import com.fiap.selfordermanagement.domain.errors.ErrorType
import createCustomerRequest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest
@WithPostgreSQL
@Disabled
class CustomerIntegrationTest {
    @LocalServerPort
    private val port: Int? = null

    @Autowired
    private lateinit var customerRepository: CustomerGateway

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()

        RestAssured.baseURI = "http://localhost:$port"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `should succeed to manage and search customers in the happy path`() {
        val customerRequest =
            createCustomerRequest(
                name = "John Doe",
            )

        // create
        given()
            .contentType(ContentType.JSON)
            .body(customerRequest)
            .`when`()
            .post("/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "document", equalTo(customerRequest.document),
                "name", equalTo(customerRequest.name),
                "email", equalTo(customerRequest.email),
                "phone", equalTo(customerRequest.phone),
                "address", equalTo(customerRequest.address),
            )

        val customer = customerRepository.findAll()[0]

        // list all
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                ".",
                hasSize<Int>(1),
                "[0].document",
                equalTo(customer.document),
            )

        // get
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .get("/customers/${customer.document}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "document",
                equalTo(customer.document),
            )

        // search
        given()
            .contentType(ContentType.JSON)
            .param("name", " john ")
            .`when`()
            .get("/customers/search")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                ".",
                hasSize<Int>(1),
                "[0].document",
                equalTo(customer.document),
            )

        // update
        val changed = customerRequest.copy(email = "changed@newemail.com")
        given()
            .contentType(ContentType.JSON)
            .body(changed)
            .`when`()
            .put("/customers/${customer.id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "email",
                equalTo(changed.email),
            )

        assertThat(customerRepository.findById(customer.id)?.email).isEqualTo(changed.email)

        // remove
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/customers/${customerRequest.document}")
            .then()
            .statusCode(HttpStatus.OK.value())

        assertThat(customerRepository.findAll()).isEmpty()
    }

    @Test
    fun `should handle corner cases accordingly`() {
        val customer = createCustomerRequest()

        // create
        given()
            .contentType(ContentType.JSON)
            .body(customer)
            .`when`()
            .post("/customers")
            .then()
            .statusCode(HttpStatus.OK.value())

        // try to create with same identifier
        given()
            .contentType(ContentType.JSON)
            .body(customer)
            .`when`()
            .post("/customers")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .body(
                "error",
                equalTo(ErrorType.CUSTOMER_ALREADY_EXISTS.name),
            )

        assertThat(customerRepository.findAll()).hasSize(1)

        val nonExistentCustomer = createCustomerRequest(document = "11122233344")

        // try to update non-existent
        given()
            .contentType(ContentType.JSON)
            .body(nonExistentCustomer)
            .`when`()
            .put("/customers/${nonExistentCustomer.document}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(
                "error",
                equalTo(ErrorType.CUSTOMER_NOT_FOUND.name),
            )

        // try to delete non-existent
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/customers/${nonExistentCustomer.document}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(
                "error",
                equalTo(ErrorType.CUSTOMER_NOT_FOUND.name),
            )
    }
}
