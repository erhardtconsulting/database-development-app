package ch.hftm.db2.ticketsystem.ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByStatusOrderByCreatedAtDesc(String status);
}

