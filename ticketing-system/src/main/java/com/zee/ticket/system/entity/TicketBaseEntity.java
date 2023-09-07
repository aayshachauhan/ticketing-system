package com.zee.ticket.system.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_base")
public class TicketBaseEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ticketId;

	@Column(name = "ticket_code")
	private String ticketCode;

	@OneToMany(mappedBy = "ticketBase", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TicketContentEntity> ticketContents = new ArrayList<>();

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "forward_owner_id")
	private String forwardOwnerUserId;

	@Column(name = "status")
	private String status;

	@PrePersist
	public void onPersist() {
		setCreateDate(new Timestamp(new Date().getTime()));
	}
}
