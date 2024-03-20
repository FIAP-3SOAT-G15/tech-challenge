package com.fiap.selfordermanagement.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class JWTSecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .authorizeHttpRequests { authorize ->
                authorize.requestMatchers(HttpMethod.POST, "/orders").permitAll()
                authorize.anyRequest().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())
                }
            }

        return http.build()
    }

    private fun grantedAuthoritiesExtractor(): JwtAuthenticationConverter {
        val jwtAuthenticationConverter = JwtAuthenticationConverter()

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter { jwt ->
            val list: List<String> = jwt.claims.getOrDefault("cognito:groups", emptyList<String>()) as List<String>

            return@setJwtGrantedAuthoritiesConverter list
                .map { obj: Any -> obj.toString() }
                .map { role -> SimpleGrantedAuthority(role) }
        }

        return jwtAuthenticationConverter
    }
}
