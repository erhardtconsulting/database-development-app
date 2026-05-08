package ch.hftm.db2.ticketsystem.ticket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;

class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final TicketService ticketService = new TicketService(ticketRepository, new TicketMapper());

    @Test
    void usesStatusRepositoryMethodWhenStatusFilterIsPresent() {
        when(ticketRepository.findByStatusOrderByCreatedAtDesc("open"))
                .thenReturn(List.of(new TicketEntity("VPN pruefen", "open")));

        List<TicketResponse> tickets = ticketService.findTickets("open");

        assertThat(tickets).extracting(TicketResponse::title).containsExactly("VPN pruefen");
        verify(ticketRepository).findByStatusOrderByCreatedAtDesc("open");
    }

    @Test
    void savesMappedTicketWhenCreatingTicket() {
        when(ticketRepository.save(any(TicketEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TicketResponse ticket = ticketService.createTicket(new CreateTicketRequest("API pruefen", "open"));

        assertThat(ticket.title()).isEqualTo("API pruefen");
        assertThat(ticket.status()).isEqualTo("open");
    }
}

