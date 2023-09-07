package com.zee.ticket.system.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileUploadResponse {

	private String fileName;

	private String fileUrl;
	
	private String errorMsg;

	@Override
	public String toString() {
		return "FileUploadResponse [fileName=" + fileName + ", fileUrl=" + fileUrl + "]";
	}
}
