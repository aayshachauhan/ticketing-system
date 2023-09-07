package com.zee.ticket.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.TicketJourneyEntity;

@Repository
public interface TicketJourneyRepo extends JpaRepository<TicketJourneyEntity, Long> {

	TicketJourneyEntity findByTicketCode(String ticketCode);

}
