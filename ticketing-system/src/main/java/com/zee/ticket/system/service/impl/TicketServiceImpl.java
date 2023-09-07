package com.zee.ticket.system.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zee.ticket.system.config.DocumentUploadCoreConfig;
import com.zee.ticket.system.entity.TicketBaseEntity;
import com.zee.ticket.system.entity.TicketContentEntity;
import com.zee.ticket.system.entity.TicketContentJourneyEntity;
import com.zee.ticket.system.entity.TicketJourneyEntity;
import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.repository.TicketBaseRepo;
import com.zee.ticket.system.repository.TicketContentJourneyRepo;
import com.zee.ticket.system.repository.TicketContentRepo;
import com.zee.ticket.system.repository.TicketJourneyRepo;
import com.zee.ticket.system.request.TicketRequest;
import com.zee.ticket.system.response.BaseResponse;
import com.zee.ticket.system.response.FileUploadResponse;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.service.StageService;
import com.zee.ticket.system.service.TicketService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService {

	private static final String INIT_DRAFT = "INIT_DRAFT";

	private static final String INIT = "INIT";

	private static final String COMPLETE = "COMPLETE";

	private static final String DRAFT = "DRAFT";

	@Autowired
	private TicketBaseRepo ticketBaseRepo;

	@Autowired
	private TicketContentRepo ticketContentRepo;

	@Autowired
	private TicketJourneyRepo ticketJourneyRepo;

	@Autowired
	private TicketContentJourneyRepo ticketContentJourneyRepo;

	@Autowired
	private StageService stageService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DocumentUploadCoreConfig documentUploadCoreConfig;

	@Override
	@Transactional(transactionManager = "zshopDataSourceTransactionManager", rollbackFor = Exception.class)
	public String saveTicket(TicketRequest request) throws BusinessException {

		String nextStageCode = request.getNextStageCode();
		String currentStatgeCode = request.getCurrentStatgeCode();

		String ticketCode = request.getTicketCode();
		if (StringUtils.isNotEmpty(ticketCode)) {

			if (StringUtils.isEmpty(nextStageCode) && StringUtils.isEmpty(currentStatgeCode))
				throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
						"Request validation failed, please provide currentStageCode and nextStageCode", null);

			TicketBaseEntity ticketBase = ticketBaseRepo.findByTicketCode(request.getTicketCode());

			if (ObjectUtils.isNotEmpty(ticketBase)) {
				log.info("Starting ticket update process ::");

				String ticketCodeReq = request.getTicketCode();
				TicketContentEntity ticketContent = ticketContentRepo.findByTicketCodeAndCreatedBy(ticketCodeReq,
						request.getUserId());

				ticketContent = currentStageProcess(request, currentStatgeCode, ticketContent, ticketBase);

				nextStatgeProcess(request, nextStageCode, currentStatgeCode, ticketBase, ticketContent);

			} else
				throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
						"Ticket does not exists with given code {}, please provide valid code.",
						request.getTicketCode());

		} else {
			log.info("Starting new ticket creation process ::");
			ticketCode = createNewTicket(request, nextStageCode, currentStatgeCode);
		}

		return ticketCode;
	}

	private String createNewTicket(TicketRequest request, String nextStageCode, String currentStatgeCode)
			throws BusinessException {
		if (StringUtils.isEmpty(request.getTitle()))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Request validation failed, please provide ticket title", null);

		if (StringUtils.isNotEmpty(nextStageCode) && StringUtils.isEmpty(request.getForwardToUser()))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Please provide a forward user for nextStageCode", null);

		if (StringUtils.isNotEmpty(currentStatgeCode) && StringUtils.isEmpty(nextStageCode))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Please provide nextStageCode as well", null);

		if (StringUtils.isEmpty(currentStatgeCode) && StringUtils.isEmpty(nextStageCode)) {
			log.info("Current stage and next stage is empty. Setting INIT stages.");
			currentStatgeCode = INIT_DRAFT;
			nextStageCode = INIT;
			request.setForwardToUser(request.getUserId());
		}

		String ticketCode = generateRandomString();

		TicketContentEntity ticketContent = new TicketContentEntity();
		ticketContent.setTicketCode(ticketCode);
		ticketContent.setTitle(request.getTitle());
		ticketContent.setDescription(request.getDesc());
		ticketContent
				.setContent_status(currentStatgeCode.equalsIgnoreCase(INIT_DRAFT) ? currentStatgeCode : COMPLETE);
		ticketContent.setCreatedBy(request.getUserId());
		ticketContent.setCreateDate(new Timestamp((new Date()).getTime()));
		ticketContent.setModiDate(new Timestamp((new Date()).getTime()));

		ticketContent.setImageUrl(request.getImageUrl());
		ticketContent.setDocumentUrl(request.getDocumentUrl());
		ticketContent.setVideoUrl(request.getVideoUrl());

		TicketBaseEntity ticketBase = new TicketBaseEntity();
		ticketBase.setTicketCode(ticketCode);
		ticketBase.setTicketContents(Arrays.asList(ticketContent));
		ticketBase.setStatus(currentStatgeCode);
		ticketBase.setCreatedBy(request.getUserId());
		ticketContent.setComments(request.getComments());

		ticketContent.setTicketBase(ticketBase);

		if (StringUtils.isNotEmpty(nextStageCode) && isValidNextStage(currentStatgeCode, nextStageCode)
				&& StringUtils.isNotEmpty(request.getForwardToUser())) {

			request.setTicketCode(ticketCode);
			log.info("A new ticket has been created with DRAFT stage. Sending to process based on next stage : {}",
					nextStageCode);

			ticketBase.setForwardOwnerUserId(request.getForwardToUser());
			ticketBase.setStatus(nextStageCode);
			ticketBaseRepo.save(ticketBase);

			saveTicketJourneyProcess(request, nextStageCode, currentStatgeCode, ticketContent);

		} else {
			log.info("A new ticket has been created in a DRAFT stage.");
			ticketBaseRepo.save(ticketBase);
		}

		return ticketCode;
	}

	private void saveTicketJourneyProcess(TicketRequest request, String nextStageCode, String currentStatgeCode,
			TicketContentEntity ticketContent) {
		TicketJourneyEntity ticketJourneyEntity = new TicketJourneyEntity();
		ticketJourneyEntity.setTicketCode(request.getTicketCode());
		ticketJourneyEntity.setCurrentOwnerId(request.getForwardToUser());
		ticketJourneyEntity.setPreviousOwnerId(request.getUserId());
		ticketJourneyEntity.setFromStage(currentStatgeCode);
		ticketJourneyEntity.setToStage(nextStageCode);
		ticketJourneyRepo.save(ticketJourneyEntity);

		TicketContentJourneyEntity tcjEn = new TicketContentJourneyEntity();
		tcjEn.setTicketCode(request.getTicketCode());
		tcjEn.setContentId(ticketContent.getContentId());
		tcjEn.setJourneyId(ticketJourneyEntity.getTicketJourneyId());
		ticketContentJourneyRepo.save(tcjEn);
	}

	private void nextStatgeProcess(TicketRequest request, String nextStageCode, String currentStatgeCode,
			TicketBaseEntity ticketBase, TicketContentEntity ticketContent) throws BusinessException {

		if (!isValidStage(nextStageCode))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Please provide a valid nextStageCode", null);

		boolean isValid = isValidNextStage(currentStatgeCode, nextStageCode);
		if (isValid) {

			if (ObjectUtils.isNotEmpty(ticketContent)) {
				List<TicketContentEntity> existingTicketContents = ticketBase.getTicketContents();
				if (CollectionUtils.isEmpty(existingTicketContents))
					existingTicketContents = new ArrayList<>();

				existingTicketContents.add(ticketContent);

				ticketBase.setTicketContents(existingTicketContents);

			}

			ticketBase.setForwardOwnerUserId(request.getForwardToUser());
			ticketBase.setStatus(nextStageCode);
			ticketBaseRepo.save(ticketBase);

			saveTicketJourneyProcess(request, nextStageCode, currentStatgeCode, ticketContent);

		} else
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Please provide a allowed currentStatgeCode and nextStageCode", null);
	}

	private TicketContentEntity currentStageProcess(TicketRequest request, String currentStatgeCode,
			TicketContentEntity ticketContent, TicketBaseEntity ticketBase) throws BusinessException {

		if (!isValidStage(currentStatgeCode))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"Please provide a valid currentStageCode", null);

		if (currentStatgeCode.equalsIgnoreCase(INIT)) {

			if (StringUtils.isNotEmpty(request.getImageUrl()))
				ticketContent.setImageUrl(request.getImageUrl());
			if (StringUtils.isNotEmpty(request.getDocumentUrl()))
				ticketContent.setDocumentUrl(request.getDocumentUrl());
			if (StringUtils.isNotEmpty(request.getVideoUrl()))
				ticketContent.setVideoUrl(request.getVideoUrl());
			if (StringUtils.isNotEmpty(request.getDesc()))
				ticketContent.setDescription(request.getDesc());
			if (StringUtils.isNotEmpty(request.getTitle()))
				ticketContent.setTitle(request.getTitle());
			if (StringUtils.isNotEmpty(request.getComments()))
				ticketContent.setComments(request.getComments());

			ticketContent.setModiDate(new Timestamp((new Date()).getTime()));
			ticketContent.setContent_status(COMPLETE);
			ticketContentRepo.save(ticketContent);

		} else {
			if (ObjectUtils.isEmpty(ticketContent))
				ticketContent = new TicketContentEntity();

			ticketContent.setTitle(request.getTitle());
			ticketContent.setDescription(request.getDesc());
			ticketContent.setImageUrl(request.getImageUrl());
			ticketContent.setDocumentUrl(request.getDocumentUrl());
			ticketContent.setVideoUrl(request.getVideoUrl());
			ticketContent.setComments(request.getComments());

			if (ObjectUtils.isNotEmpty(ticketContent)) {
				ticketContent.setCreatedBy(request.getUserId());
				ticketContent.setContent_status(currentStatgeCode.contains(DRAFT) ? currentStatgeCode : COMPLETE);
				ticketContent.setCreateDate(new Timestamp((new Date()).getTime()));
				ticketContent.setModiDate(new Timestamp((new Date()).getTime()));
				ticketContent.setTicketBase(ticketBase);
				ticketContent.setTicketCode(request.getTicketCode());
				ticketContentRepo.save(ticketContent);
			}

		}

		return ticketContent;
	}

	private boolean isValidStage(String stageCode) {
		return stageService.isStageExists(stageCode);
	}

	private boolean isValidNextStage(String currentStatgeCode, String nextStageCode) {
		return stageService.isValidStageMovement(currentStatgeCode, nextStageCode);
	}

	public static String generateRandomString() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] randomBytes = new byte[15];
		secureRandom.nextBytes(randomBytes);

		String base64Encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

		String randomString = base64Encoded.replaceAll("[^a-zA-Z0-9]", "");

		return randomString.substring(0, 15);
	}

	@Override
	public FileUploadResponse uploadTicketDocument(String requestJson, MultipartFile multipartFile)
			throws IOException, BusinessException {

		String originalFilename = multipartFile.getOriginalFilename();
		log.info("Image file recieved : {}", originalFilename);
		log.info("request recieved {}", requestJson);

		validateRequest(requestJson);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
		multipartBodyBuilder.part("file", multipartFile.getBytes()).header(HttpHeaders.CONTENT_DISPOSITION,
				"form-data; name=\"file\"; filename=\"" + originalFilename + "\"");
		multipartBodyBuilder.part("request", requestJson);

		MultiValueMap<String, HttpEntity<?>> parts = multipartBodyBuilder.build();

		HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(parts, headers);

		ResponseEntity<BaseResponse> response = restTemplate
				.exchange(
						new StringBuilder(documentUploadCoreConfig.getBaseUrl())
								.append(documentUploadCoreConfig.getUploadDoc()).toString(),
						HttpMethod.POST, requestEntity, BaseResponse.class);

		FileUploadResponse docUplaodResp = new FileUploadResponse();
		if (response.getStatusCode() == HttpStatus.OK && ObjectUtils.isNotEmpty(response.getBody().getData())) {
			Object respData = response.getBody().getData();
			Gson gson = new Gson();
			String jsonData = gson.toJson(respData);

			Type responseType = new TypeToken<List<FileUploadResponse>>() {
			}.getType();
			List<FileUploadResponse> fileUploadResponses = gson.fromJson(jsonData, responseType);

			if (!CollectionUtils.isEmpty(fileUploadResponses))
				docUplaodResp = fileUploadResponses.get(0);

		} else {
			docUplaodResp.setErrorMsg("Error occurred while sending multipart file: " + response.getBody());
		}

		return docUplaodResp;
	}

	private void validateRequest(String requestJson)
			throws JsonProcessingException, JsonMappingException, BusinessException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(requestJson);

		String objectUniqueIdentifier = jsonNode.get("objectUniqueIdentifier").asText();
		String userId = jsonNode.get("userId").asText();

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(objectUniqueIdentifier))
			throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
					"userId and ticketCode (objectUniqueIdentifier) are mandatory", null);
	}

}
