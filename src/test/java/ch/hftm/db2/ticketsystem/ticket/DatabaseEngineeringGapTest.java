package ch.hftm.db2.ticketsystem.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("guided-gap")
class DatabaseEngineeringGapTest {

    @Test
    void migrationContainsDatabaseRulesForTickets() throws IOException {
        String migration = Files.readString(Path.of("src/main/resources/db/migration/V1__starter_ticket_schema.sql"));

        assertThat(migration)
                .as("Tickets brauchen datenbankseitige Pflichtfelder.")
                .contains("title TEXT NOT NULL")
                .contains("status TEXT NOT NULL");

        assertThat(migration)
                .as("Gueltige Ticket-Status sollen in PostgreSQL abgesichert werden.")
                .contains("CHECK (status IN");
    }
}

