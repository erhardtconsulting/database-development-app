package ch.hftm.db2.ticketsystem.ticket;

import org.springframework.stereotype.Component;

@Component
class TicketMapper {

    TicketResponse toResponse(TicketEntity entity) {
        return new TicketResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    TicketEntity toEntity(CreateTicketRequest request) {
        return new TicketEntity(request.getTitle(), request.getStatus());
    }
}
