package com.zee.ticket.system.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_journey")
public class TicketJourneyEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketJourneyId;
	
	@Column(name = "ticket_code")
	private String ticketCode;

	@Column(name = "previous_owner_id")
	private String previousOwnerId;
	
	@Column(name = "current_owner_id")
	private String currentOwnerId;

	@Column(name = "from_stage")
	private String fromStage;
	
	@Column(name = "to_stage")
	private String toStage;
	
	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "modi_date")
	private Date modiDate;

	@PrePersist
	public void onPersist() {
		this.setCreateDate(new Timestamp((new Date()).getTime()));
		onUpdate();
	}

	@PreUpdate
	public void onUpdate() {
		this.setModiDate(new Timestamp((new Date()).getTime()));
	}
}
