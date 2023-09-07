package com.zee.ticket.system.request;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadRequest {

	private MultipartFile file;

	private InputStream inputStream;
	private String fileName;

	private String objectUniqueIdentifier;

	private String userId;

	@Override
	public String toString() {
		return "DocumentUploadRequest [file=" + file + ", inputStream=" + inputStream + ", objectUniqueIdentifier="
				+ objectUniqueIdentifier + ", userId=" + userId + "]";
	}

}
