package com.zee.ticket.system.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.exception.DaoException;
import com.zee.ticket.system.request.StageRequest;
import com.zee.ticket.system.response.BaseResponse;
import com.zee.ticket.system.response.BaseResponseBuilder;
import com.zee.ticket.system.response.StageResponse;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.service.StageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StageController {

	@Autowired
	StageService stageService;
	
	@GetMapping("/v1/get/stageDetails")
	public ResponseEntity<BaseResponse> readyByStageCode(@Valid @RequestBody StageRequest stageRequest)
			throws BusinessException, IOException, DaoException, ServiceException, URISyntaxException {

		log.info("Request for Stage Management:: {}", stageRequest);
		StageResponse stageResponse = stageService.readByStageCode(stageRequest);
		BaseResponse buildSellerDetailsResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndData(
				HttpStatus.OK.name(), StatusCodeEnum.SUCCESS_CODE.getCode(), stageResponse);
		return new ResponseEntity<>(buildSellerDetailsResponse,
				HttpStatus.valueOf(buildSellerDetailsResponse.getStatus()));
	}

}
