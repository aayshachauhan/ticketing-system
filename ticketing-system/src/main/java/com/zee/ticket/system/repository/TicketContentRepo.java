package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.TicketContentEntity;

@Repository
public interface TicketContentRepo extends JpaRepository<TicketContentEntity, Long> {

	List<TicketContentEntity> findByTicketCode(String ticketCode);

	TicketContentEntity findByTicketCodeAndCreatedBy(String ticketCodeReq, String userId);

}
