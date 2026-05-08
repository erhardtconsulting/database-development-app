package ch.hftm.db2.ticketsystem.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CreateTicketRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String status;
}
