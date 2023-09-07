package com.zee.ticket.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.ticket.system.dtos.StageEntityDto;
import com.zee.ticket.system.dtos.SubstageEntityDto;
import com.zee.ticket.system.entity.StageEntity;
import com.zee.ticket.system.entity.StageSubstageMappingEntity;
import com.zee.ticket.system.entity.SubstageEntity;
import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.repository.StageRepo;
import com.zee.ticket.system.repository.StageSubstageMappingRepo;
import com.zee.ticket.system.repository.SubstageRepo;
import com.zee.ticket.system.request.StageRequest;
import com.zee.ticket.system.response.StageResponse;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.service.StageService;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
@Service
public class StageServiceImpl implements StageService {

	@Autowired
	StageRepo stageRepo;

	@Autowired
	SubstageRepo substageRepo;

	@Autowired
	private StageSubstageMappingRepo stageSubstageMappingRepo;

	@Override
	public Boolean isStageExists(String stageCode) {

		Boolean isExists = Boolean.FALSE;

		StageEntity stageMaster = stageRepo.findByStageCode(stageCode);

		SubstageEntity subStageMaster = substageRepo.findBySubStageCode(stageCode);

		if (!ObjectUtils.isEmpty(stageMaster) || !ObjectUtils.isEmpty(subStageMaster))
			isExists = Boolean.TRUE;

		return isExists;
	}

	@Override
	public Boolean isValidStageMovement(String fromStage, String toStage) {

		Boolean isExists = Boolean.FALSE;

		StageEntity fromStageEn = stageRepo.findByStageCode(fromStage);

		SubstageEntity toStageEn = substageRepo.findBySubStageCode(toStage);

		if (!ObjectUtils.isEmpty(fromStageEn) && !ObjectUtils.isEmpty(toStageEn)) {
			if (!ObjectUtils.isEmpty(stageSubstageMappingRepo.findByStageIdAndSubStageId(fromStageEn.getStageId(),
					toStageEn.getSubStageId())))
				isExists = Boolean.TRUE;
		}

		return isExists;
	}

	@Override
	public StageResponse readByStageCode(@Valid StageRequest stageRequest) throws BusinessException {
		StageResponse stageResponse = new StageResponse();
		ObjectMapper mapper = new ObjectMapper();

		if (!ObjectUtils.isEmpty(stageRequest.getStageCode())) {
			StageEntity stageEntity = stageRepo.findByStageCodeAndIsActive(stageRequest.getStageCode(), "Y");

			if (stageEntity != null) {
				Long stageId = stageEntity.getStageId();

				List<StageSubstageMappingEntity> stageSubstageMappingEntity = stageSubstageMappingRepo
						.findByStageIdAndIsActive(stageId, "Y");
				List<Long> subStageIds = stageSubstageMappingEntity.stream()
						.map(StageSubstageMappingEntity::getSubStageId).collect(Collectors.toList());

				List<SubstageEntity> substageEntities = substageRepo.findBySubStageIdInAndIsActive(subStageIds, "Y");
				List<SubstageEntityDto> substageEntityDtos = substageEntities.stream()
						.map(subStageEntity -> mapper.convertValue(subStageEntity, SubstageEntityDto.class))
						.collect(Collectors.toList());

				log.info("All Mapped Data fetch by stage Code");
				stageResponse.setSubstageEntityDtos(substageEntityDtos);

			} else {
				log.error("Stage code does not exist or inactive");
				throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
						"Stage code does not exist or inactive, Please put existed and active stage code. ",
						stageRequest.getStageCode());
			}
		} else {

			List<StageEntityDto> allEntityDtos = new ArrayList<>();
			boolean anyActiveDataMapped = false;

			List<StageEntity> allEntities = stageRepo.findAll();

			for (StageEntity stageEnt : allEntities) {
				if (stageEnt.getIsActive().equals("Y") && stageEnt != null) {
					Long stageId = stageEnt.getStageId();

					List<StageSubstageMappingEntity> stageSubstageMappingEntity = stageSubstageMappingRepo
							.findByStageIdAndIsActive(stageId, "Y");

					if (!stageSubstageMappingEntity.isEmpty()) {
						StageEntityDto stageEntityDtoList = mapper.convertValue(stageEnt, StageEntityDto.class);

						List<Long> subStageIds = stageSubstageMappingEntity.stream()
								.map(StageSubstageMappingEntity::getSubStageId).collect(Collectors.toList());

						List<SubstageEntity> substageEnt = substageRepo.findBySubStageIdInAndIsActive(subStageIds, "Y");

						List<SubstageEntityDto> substageEntityDtoes = new ArrayList<>();

						for (SubstageEntity subStageEntities : substageEnt) {
							SubstageEntityDto subStageEntityDtos = mapper.convertValue(subStageEntities,
									SubstageEntityDto.class);
							substageEntityDtoes.add(subStageEntityDtos);
						}
						stageEntityDtoList.setSubstageCodes(substageEntityDtoes);
						allEntityDtos.add(stageEntityDtoList);
						
						anyActiveDataMapped = true;

					}
				}
			}
			if (!anyActiveDataMapped) {
		        log.error("No Active data mapped in Stage Management");
		        throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
		                "No Active data mapped in Stage Management", stageRequest.getStageCode());
		    }
			log.info("All Active Data fetch by stage Code");
			stageResponse.setStageEntityDtos(allEntityDtos);
		}

		return stageResponse;
	}

}
