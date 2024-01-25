import org.junit.jupiter.api.Tag
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Tag("IntegrationTest")
@ActiveProfiles("test")
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class IntegrationTest

@ContextConfiguration(initializers = [PostgreSQLContainerInitializer::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class WithPostgreSQL
