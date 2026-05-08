package ch.hftm.db2.ticketsystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "DB-2 Ticket System API",
                version = "0.1.0",
                description = "Spring-Boot-Starterprojekt für DB-2 mit bewusst offenen Guided Gaps in der Datenbanklogik."
        )
)
class OpenApiConfig {
}
