package ch.hftm.db2.ticketsystem.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("guided-gap")
class DatabaseEngineeringGapTest {

    @Test
    void ticketRulesAreAddedWithNewMigration() throws IOException {
        Path v1Path = Path.of("src/main/resources/db/migration/V1__starter_ticket_schema.sql");
        Path v2Path = Path.of("src/main/resources/db/migration/V2__enforce_ticket_rules.sql");

        String v1 = Files.readString(v1Path);
        assertThat(v1)
                .as("V1 bleibt der bewusst schwache Ausgangszustand. Schreibe eine neue V2-Migration.")
                .doesNotContain("title TEXT NOT NULL")
                .doesNotContain("status TEXT NOT NULL")
                .doesNotContain("CHECK (status IN");

        assertThat(v2Path)
                .as("Lege eine neue Migration src/main/resources/db/migration/V2__enforce_ticket_rules.sql an.")
                .exists();

        String v2 = Files.readString(v2Path).replaceAll("\\s+", " ").toUpperCase(Locale.ROOT);

        assertThat(v2)
                .as("Tickets brauchen datenbankseitige Pflichtfelder in einer neuen Migration.")
                .contains("ALTER TABLE APP_STARTER.TICKETS ALTER COLUMN TITLE SET NOT NULL")
                .contains("ALTER TABLE APP_STARTER.TICKETS ALTER COLUMN STATUS SET NOT NULL");

        assertThat(v2)
                .as("Gueltige Ticket-Status sollen in PostgreSQL abgesichert werden.")
                .contains("CHECK (STATUS IN")
                .contains("'OPEN'")
                .contains("'WAITING'")
                .contains("'CLOSED'");
    }
}
