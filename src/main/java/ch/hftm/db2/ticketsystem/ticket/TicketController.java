package ch.hftm.db2.ticketsystem.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Tickets", description = "Ticket Controller")
class TicketController {

    private final TicketService ticketService;

    TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Operation(summary = "Tickets lesen", description = "Liest alle Tickets oder filtert nach einem Status.")
    List<TicketResponse> findTickets(
            @Parameter(description = "Optionaler Statusfilter, zum Beispiel open")
            @RequestParam(required = false) String status
    ) {
        return ticketService.findTickets(status);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ticket erstellen", description = "Erstellt ein minimales Ticket im Starter-Schema app_starter.")
    TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ticketService.createTicket(request);
    }
}
