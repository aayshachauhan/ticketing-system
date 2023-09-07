package com.zee.ticket.system.response;

import java.io.Serializable;

public class BaseResponseBuilder extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -6478087801033708055L;

	public BaseResponse setBaseResponseWithStatusAndCode(String status, Integer code) {
		return new BaseResponse(status, code);
	}

	public BaseResponse setBaseResponseWithStatusAndCodeAndData(String status, Integer code, Object data) {
		return new BaseResponse(status, code).withData(data);

	}

	public BaseResponse setBaseResponseWithStatusAndCodeAndCustomMessage(String status, Integer code, String msg) {
		return new BaseResponse(status, code).withCustomMessage(msg);
	}

	public BaseResponse setBaseResponseWithStatusAndCodeAndCustomMessageAndData(String status, Integer code, String msg,
			Object data) {
		return new BaseResponse(status, code).withCustomMessage(msg).withData(data);

	}
}
