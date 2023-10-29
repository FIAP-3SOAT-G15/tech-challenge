package com.fiap.selfordermanagement.application.services

import IntegrationTest
import WithPostgreSQL
import com.fiap.selfordermanagement.application.domain.errors.ErrorType
import com.fiap.selfordermanagement.application.ports.outgoing.CustomerRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@IntegrationTest
@WithPostgreSQL
class CustomerIntegrationTest {
    @LocalServerPort
    private val port: Int? = null

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
        RestAssured.baseURI = "http://localhost:$port"
    }

    @Test
    fun `should succeed to manage and search customers in the happy path`() {
        val customer =
            createCustomer(
                name = "Jonh Doe",
            )

        // create
        given()
            .contentType(ContentType.JSON)
            .body(customer)
            .`when`()
            .post("/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "document", equalTo(customer.document),
                "name", equalTo(customer.name),
                "email", equalTo(customer.email),
                "phone", equalTo(customer.phone),
                "address", equalTo(customer.address),
            )

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
            .param("name", "Doe")
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
        val changed = customer.copy(email = "changed@newemail.com")
        given()
            .contentType(ContentType.JSON)
            .body(changed)
            .`when`()
            .put("/customers/${customer.document}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(
                "email",
                equalTo(changed.email),
            )

        assertThat(customerRepository.findByDocument(customer.document)?.email).isEqualTo(changed.email)

        // remove
        given()
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/customers/${customer.document}")
            .then()
            .statusCode(HttpStatus.OK.value())

        assertThat(customerRepository.findAll()).isEmpty()
    }

    @Test
    fun `should handle corner cases accordingly`() {
        val customer = createCustomer()

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

        val nonExistentCustomer = createCustomer(document = "11122233344")

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
