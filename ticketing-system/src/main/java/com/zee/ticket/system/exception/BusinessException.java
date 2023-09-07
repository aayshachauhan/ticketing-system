package com.zee.ticket.system.exception;

public class BusinessException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String errorCode, String errorMessage, Throwable cause) {
		super(errorCode, errorMessage, cause);
	}

	public BusinessException(Integer statusCode, String errorMessage, Throwable cause) {
		super(statusCode, errorMessage, cause);
	}
	
	public BusinessException(Integer statusCode, String errorMessage,Object data) {
		super(statusCode, errorMessage ,data);
	}
	

}

