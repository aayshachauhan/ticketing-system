package com.zee.ticket.system.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.request.TicketRequest;
import com.zee.ticket.system.response.FileUploadResponse;

public interface TicketService {

	String saveTicket(TicketRequest request) throws BusinessException;

	FileUploadResponse uploadTicketDocument(String request, MultipartFile multipartFile) throws IOException, BusinessException;

}
