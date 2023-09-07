package com.zee.ticket.system.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket_content")
public class TicketContentEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contentId;

	@Column(name = "ticket_code")
	private String ticketCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_base_id")
	private TicketBaseEntity ticketBase;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "document_url")
	private String documentUrl;

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "comments")
	private String comments;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "content_status")
	private String content_status;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "modi_date")
	private Date modiDate;

}
