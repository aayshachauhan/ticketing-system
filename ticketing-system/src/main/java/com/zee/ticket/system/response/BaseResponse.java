package com.zee.ticket.system.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseResponse implements Serializable {

	private static final long serialVersionUID = 4773693334019107909L;

	private String status;
	private Integer code;
	private String message;
	private Object data;

	public BaseResponse withData(Object data) {
		this.data = data;
		return this;
	}

	public BaseResponse withCustomMessage(String message) {
		this.message = message;
		return this;
	}

	private Integer getValidCode(Integer code) {
		return (code == null) ? StatusCodeEnum.ILLEGAL_STATUS_CODE.getCode() : code;
	}

	protected BaseResponse(String status, Integer code) {
		super();
		this.status = status;
		this.code = getValidCode(code);
		this.message = StatusCodeEnum.findByCode(code).getMessage();
	}

	protected BaseResponse() {
	}
}
