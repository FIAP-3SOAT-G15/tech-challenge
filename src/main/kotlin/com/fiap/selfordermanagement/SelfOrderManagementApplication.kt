package com.fiap.selfordermanagement

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info =
        Info(
            title = "Self-Order API",
            version = "1.0.0",
            description =
                "API de autoatendimento em restaurante como implementação do Tech Challenge" +
                    " referente à primeira fase do curso de pós-graduação em Arquitetura de Software pela FIAP.",
            contact =
                Contact(
                    name = "Grupo 15",
                    url = "http://fiap-3soat-g15.s3-website-sa-east-1.amazonaws.com",
                ),
        ),
)
class SelfOrderManagementApplication

fun main(args: Array<String>) {
    runApplication<SelfOrderManagementApplication>(*args)
}
