package com.zee.ticket.system.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.request.StageRequest;
import com.zee.ticket.system.response.StageResponse;

@Service
public interface StageService {

	public Boolean isStageExists(String stageCode);

	public Boolean isValidStageMovement(String fromStage, String toStage);

	public StageResponse readByStageCode(@Valid StageRequest stageRequest) throws BusinessException;

}
