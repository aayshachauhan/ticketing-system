package com.zee.ticket.system.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.exception.DaoException;
import com.zee.ticket.system.request.TicketRequest;
import com.zee.ticket.system.response.BaseResponse;
import com.zee.ticket.system.response.BaseResponseBuilder;
import com.zee.ticket.system.response.FileUploadResponse;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@PostMapping("/v1/ticket/process")
	@CrossOrigin
	public ResponseEntity<BaseResponse> ticketProcess(@Valid @RequestBody TicketRequest request)
			throws BusinessException, IOException, DaoException, ServiceException, URISyntaxException {

		log.info("Generate or process ticket in system:: {}", request);
		String saveTicketCode = ticketService.saveTicket(request);
		BaseResponse buildSellerDetailsResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
				HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(), saveTicketCode);
		return new ResponseEntity<>(buildSellerDetailsResponse,
				HttpStatus.valueOf(buildSellerDetailsResponse.getStatus()));
	}
	
	@PostMapping("/v1/ticket/document/upload")
	@CrossOrigin
	public ResponseEntity<BaseResponse> ticketDocUpload(@Valid @RequestParam("request") String request,
			@RequestParam(value = "file", required = true) MultipartFile multipartFile)
			throws BusinessException, IOException, DaoException, ServiceException, URISyntaxException {

		log.info("Generate or process ticket in system:: {}", request);
		FileUploadResponse saveTicketCode = ticketService.uploadTicketDocument(request,multipartFile);
		BaseResponse buildSellerDetailsResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
				HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(), saveTicketCode);
		return new ResponseEntity<>(buildSellerDetailsResponse,
				HttpStatus.valueOf(buildSellerDetailsResponse.getStatus()));
	}
}
