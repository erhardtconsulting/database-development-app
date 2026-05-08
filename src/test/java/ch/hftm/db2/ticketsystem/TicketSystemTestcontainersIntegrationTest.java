package ch.hftm.db2.ticketsystem;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("testcontainers")
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketSystemTestcontainersIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("ticket_system")
            .withUsername("ticket_user")
            .withPassword("ticket_user");

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void configurePostgresql(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void migratesDatabaseAndServesTicketApi() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> getResponse = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + "/api/tickets"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        assertThat(getResponse.statusCode()).isEqualTo(200);
        assertThat(getResponse.body()).contains("Reporting-Abfrage pruefen", "VPN-Zugriff analysieren");

        HttpResponse<String> postResponse = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + "/api/tickets"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString("""
                                {
                                  "title": "Testcontainers pruefen",
                                  "status": "open"
                                }
                                """))
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        assertThat(postResponse.statusCode()).isEqualTo(201);
        assertThat(postResponse.body()).contains("Testcontainers pruefen", "open");
    }

    @Test
    void exposesSwaggerUiAndOpenApiDescription() throws Exception {
        HttpClient redirectClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpResponse<String> rootResponse = redirectClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + "/"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        assertThat(rootResponse.statusCode()).isBetween(300, 399);
        assertThat(rootResponse.headers().firstValue("Location"))
                .hasValueSatisfying(location -> assertThat(location).endsWith("/swagger-ui.html"));

        HttpResponse<String> swaggerResponse = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + "/swagger-ui.html"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        assertThat(swaggerResponse.statusCode()).isEqualTo(200);
        assertThat(swaggerResponse.body()).contains("Swagger UI");

        HttpResponse<String> openApiResponse = client.send(
                HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:" + port + "/v3/api-docs"))
                        .GET()
                        .build(),
                HttpResponse.BodyHandlers.ofString()
        );

        assertThat(openApiResponse.statusCode()).isEqualTo(200);
        assertThat(openApiResponse.body()).contains(
                "DB-2 Ticket System API",
                "/api/tickets",
                "Tickets"
        );
    }
}
