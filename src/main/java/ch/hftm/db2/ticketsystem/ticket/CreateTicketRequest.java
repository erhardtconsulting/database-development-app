package ch.hftm.db2.ticketsystem.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Eingabe für ein neues Starter-Ticket")
class CreateTicketRequest {

    @NotBlank
    @Schema(description = "Kurzer Tickettitel", example = "Datenbankverbindung prüfen")
    private String title;

    @NotBlank
    @Schema(description = "Ticketstatus im Starterprojekt", example = "open")
    private String status;
}
