package com.zee.ticket.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.TicketContentJourneyEntity;

@Repository
public interface TicketContentJourneyRepo extends JpaRepository<TicketContentJourneyEntity, Long> {

	TicketContentJourneyEntity findByTicketCode(String ticketCode);

}
