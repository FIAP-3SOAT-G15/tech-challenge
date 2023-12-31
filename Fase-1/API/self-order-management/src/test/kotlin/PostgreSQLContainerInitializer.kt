import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer

class PostgreSQLContainerInitializer :
    ApplicationContextInitializer<ConfigurableApplicationContext>,
    PostgreSQLContainer<PostgreSQLContainerInitializer>("postgres") {
    companion object {
        private val instance: PostgreSQLContainerInitializer =
            PostgreSQLContainerInitializer()
                .withDatabaseName("selforder")
                .withUsername("selforder")
                .withPassword("self@Order123!")
    }

    override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
        instance.start()
        TestPropertyValues.of(
            "spring.datasource.url=${instance.jdbcUrl}",
            "spring.datasource.username=${instance.username}",
            "spring.datasource.password=${instance.password}",
        ).applyTo(configurableApplicationContext)
    }
}
