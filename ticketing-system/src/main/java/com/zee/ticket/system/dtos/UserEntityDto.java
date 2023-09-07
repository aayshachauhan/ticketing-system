package com.zee.ticket.system.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntityDto {

	private Long id;
	
	private String userId;

	private String userName;

	private String userEmail;

	private String userpassword;

	private String sourceId;

	private String sourceName;

	private String isActive;

	private String createDate;

	private String modifyDate;

	private List<UserRoleEntityDto> userRole;

}
