package com.zee.ticket.system.response;

import java.util.Arrays;

public enum StatusCodeEnum {

	SUCCESS_CODE(2000, "Request Successful"), PARAMS_MISSING_CODE(4000, "Request parameters are missing / in-valid"),
	FILE_UPLOAD_ERROR(4005, "Failed to uplaod file.Please try again later."),
	NOT_ALLOWED(4006, "Not allowed for this operation"),
	ALREADY_EXIST_CODE(4030, "Requested parameters already exists"),
	NOT_EXIST_CODE(4031, "No data found for given criteria."), UNAUTHORIZED(4032, "Not authorzed for this request."),
	BAD_CREDENTIALS(4033, "Bad credentials provided."), EXCEPTION_CODE(5000, "Some server exception occured."),
	ILLEGAL_STATUS_CODE(-1, "Illegal status code");

	private Integer code;
	private String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	private StatusCodeEnum(Integer code, String status) {
		this.code = code;
		this.message = status;
	}

	public static StatusCodeEnum findByCode(Integer code) {

		return Arrays.stream(StatusCodeEnum.values()).filter(e -> e.getCode().equals(code)).findFirst()
				.orElse(StatusCodeEnum.ILLEGAL_STATUS_CODE);
	}
}
