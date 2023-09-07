package com.zee.ticket.system.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_content_journey_mapping")
public class TicketContentJourneyEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ticketJourneyId;

	@Column(name = "ticket_code")
	private String ticketCode;

	@Column(name = "content_id")
	private Long contentId;

	@Column(name = "journey_id")
	private Long journeyId;

	@Column(name = "create_date")
	private Date createDate;

	@PrePersist
	public void onPersist() {
		this.setCreateDate(new Timestamp((new Date()).getTime()));
	}

}
