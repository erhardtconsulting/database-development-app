package ch.hftm.db2.ticketsystem.ticket;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
    }

    @Transactional(readOnly = true)
    List<TicketResponse> findTickets(String status) {
        List<TicketEntity> tickets = StringUtils.hasText(status)
                ? ticketRepository.findByStatusOrderByCreatedAtDesc(status)
                : ticketRepository.findAll();

        return tickets.stream()
                .map(ticketMapper::toResponse)
                .toList();
    }

    @Transactional
    TicketResponse createTicket(CreateTicketRequest request) {
        TicketEntity savedTicket = ticketRepository.save(ticketMapper.toEntity(request));
        return ticketMapper.toResponse(savedTicket);
    }
}

