package com.zee.ticket.system.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zee.ticket.system.dtos.UserEntityDto;
import com.zee.ticket.system.dtos.UserRoleEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserManagementResponse {

	private List<UserEntityDto> userDetail;

	public List<UserRoleEntityDto> userRoles;
}
