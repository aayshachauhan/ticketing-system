package com.zee.ticket.system.exception;

import com.google.gson.annotations.Expose;

public class BaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private String errorCode;

	@Expose
	private String errorMessage;

	@Expose
	private Integer statusCode;

	@Expose
	private Object data;
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BaseException() {
		super();
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String errorCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public BaseException(Integer statusCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
	}

	public BaseException(Integer statusCode, String errorMessage, Object data) {
		super();
		this.errorMessage = errorMessage;
		this.statusCode = statusCode;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
}
