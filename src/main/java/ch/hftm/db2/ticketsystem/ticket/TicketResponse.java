package ch.hftm.db2.ticketsystem.ticket;

import java.time.OffsetDateTime;
import lombok.Value;

@Value
class TicketResponse {
    Long id;
    String title;
    String status;
    OffsetDateTime createdAt;
}
