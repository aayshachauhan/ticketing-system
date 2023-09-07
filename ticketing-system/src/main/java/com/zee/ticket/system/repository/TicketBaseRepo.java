package com.zee.ticket.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.TicketBaseEntity;

@Repository
public interface TicketBaseRepo extends JpaRepository<TicketBaseEntity, Long> {

	TicketBaseEntity findByTicketCode(String ticketCode);

}
