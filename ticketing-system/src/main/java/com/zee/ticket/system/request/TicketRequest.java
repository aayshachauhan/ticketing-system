package com.zee.ticket.system.request;

import lombok.Data;

@Data
public class TicketRequest {

	private String ticketCode;
	private String title;
	private String desc;

	private String imageUrl;
	private String documentUrl;
	private String videoUrl;
	
	private String comments;
	
	private String userId;
	private String forwardToUser;

	private String currentStatgeCode;
	private String nextStageCode;
}
