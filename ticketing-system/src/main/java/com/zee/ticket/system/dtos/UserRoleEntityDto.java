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
public class UserRoleEntityDto {

	private Long id;

	private String roleName;

	private String roleCode;

	private String roleDescription;

	private String isActive;

	private String createDate;

	private String modifyDate;

	public List<UserEntityDto> userEntityDtos;

}
