package dev.phelliperodrigues.volunteerAccessoryApi.application.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Phellipe Rodrigues",
                        email = "voluntarios.ccb@insightslabs.dev",
                        url = "https://voluntarios.insightslabs.dev"
                ),
                description = "Api da aplicação do sistema de controle de voluntarios CCB",
                title = "Sistema de Voluntários CCB",
                version = "1.0",
                license = @License(
                        name = "Private",
                        url = "https://voluntarios-licence.insightslabs.dev"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://voluntarios.insightslabs.dev"
                )
        }/*,
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }*/
)
/*@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)*/
public class OpenApiConfig {
}
