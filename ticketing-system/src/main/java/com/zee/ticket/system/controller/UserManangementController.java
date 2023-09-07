package com.zee.ticket.system.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zee.ticket.system.request.UserManagementRequest;
import com.zee.ticket.system.response.BaseResponse;
import com.zee.ticket.system.response.BaseResponseBuilder;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.response.UserManagementResponse;
import com.zee.ticket.system.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserManangementController {

	@Autowired
	UserService userService;

	@GetMapping("/v1/get/byUserRole")
	public ResponseEntity<BaseResponse> readByRoleCodes(@Valid @RequestBody UserManagementRequest userManagementRequest)
			throws IOException, Exception {

		log.info("Request for user Management:: {}", userManagementRequest);
		UserManagementResponse userManagementResponse = userService.readByRoleCodes(userManagementRequest);
		BaseResponse buildSellerDetailsResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
				HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(), userManagementResponse);
		return new ResponseEntity<>(buildSellerDetailsResponse,
				HttpStatus.valueOf(buildSellerDetailsResponse.getStatus()));
	}
}