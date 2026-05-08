package ch.hftm.db2.ticketsystem.ticket;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByStatusOrderByCreatedAtDesc(String status);
}

