package com.zee.ticket.system.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zee.ticket.system.response.BaseResponse;
import com.zee.ticket.system.response.BaseResponseBuilder;
import com.zee.ticket.system.response.StatusCodeEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author abhipreet
 *
 */
@EnableWebMvc
@Slf4j
@ControllerAdvice
public class TicketSystemExceptionHandler {

	/**
	 * 
	 * Exception handler for handing BAD_REQUEST
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<BaseResponse> handleBindExceptions(BindException ex) {
		log.error("Bind exception occured ", ex);
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		String errorMessage = "";
		if (!CollectionUtils.isEmpty(allErrors)) {
			errorMessage = String.join(",",
					allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
		}
		BaseResponse baseResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndCustomMessage(
				HttpStatus.BAD_REQUEST.name(), StatusCodeEnum.PARAMS_MISSING_CODE.getCode(), errorMessage);
		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Exception for argument mismatching
	 * @param ex
	 * @return
	 */

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<BaseResponse> handleArgsNotValidExceptions(MethodArgumentNotValidException ex) {
		log.error("MethodArgumentNotValidException exception occured ", ex);
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		String errorMessage = "";
		if (!CollectionUtils.isEmpty(allErrors)) {
			errorMessage = String.join(",",
					allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList()));
		}
		BaseResponse baseResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndCustomMessage(
				HttpStatus.BAD_REQUEST.name(), StatusCodeEnum.PARAMS_MISSING_CODE.getCode(), errorMessage);
		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}


	/**
	 * Exception handler for handing BusinessException
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<BaseResponse> handleBusinessExceptions(BusinessException ex) {
		log.error("Business exception occured ", ex);
		BaseResponse baseResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndCustomMessageAndData(
				HttpStatus.BAD_REQUEST.name(), ex.getStatusCode(), ex.getErrorMessage(), ex.getData());
		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * Exception handler for handing INTERNAL_SERVER_ERROR
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<BaseResponse> handleISE(Exception ex) {
		log.error("ISE occured ", ex);
		BaseResponse baseResponse = new BaseResponseBuilder().setBaseResponseWithStatusAndCodeAndCustomMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.name(), StatusCodeEnum.EXCEPTION_CODE.getCode(), ex.getMessage());
		return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
