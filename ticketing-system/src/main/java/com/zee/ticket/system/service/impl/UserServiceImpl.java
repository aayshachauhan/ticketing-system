package com.zee.ticket.system.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zee.ticket.system.dtos.UserEntityDto;
import com.zee.ticket.system.dtos.UserRoleEntityDto;
import com.zee.ticket.system.entity.UserEntity;
import com.zee.ticket.system.entity.UserRoleMapping;
import com.zee.ticket.system.entity.UserRoleMasterEntity;
import com.zee.ticket.system.exception.BusinessException;
import com.zee.ticket.system.repository.UserRepo;
import com.zee.ticket.system.repository.UserRoleMappingRepo;
import com.zee.ticket.system.repository.UserRoleRepo;
import com.zee.ticket.system.request.UserManagementRequest;
import com.zee.ticket.system.response.StatusCodeEnum;
import com.zee.ticket.system.response.UserManagementResponse;
import com.zee.ticket.system.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserRoleRepo userRoleRepo;

	@Autowired
	UserRoleMappingRepo userRoleMappingRepo;

	@Override
	public UserManagementResponse readByRoleCodes(UserManagementRequest userManagementRequest)
			throws Exception, IOException {
		UserManagementResponse userManagementResponse = new UserManagementResponse();
		ObjectMapper mapper = new ObjectMapper();
		if (!ObjectUtils.isEmpty(userManagementRequest.getRoleCode())) {
			UserRoleMasterEntity userRoleEntities = userRoleRepo
					.findByRoleCodeAndIsActive(userManagementRequest.getRoleCode(), "Y");
			if (userRoleEntities != null) {
				Long roleId = userRoleEntities.getId();
				List<UserRoleMapping> userRoleMappings = userRoleMappingRepo.findByRoleIdAndIsActive(roleId, "Y");

				List<Long> userIds = userRoleMappings.stream().map(UserRoleMapping::getUsersId)
						.collect(Collectors.toList());

				List<UserEntity> userEntities = userRepo.findByIdInAndIsActive(userIds, "Y");
				List<UserEntityDto> allUserEntityDtos = new ArrayList<>();
				for (UserEntity usrEntity : userEntities) {

					UserEntityDto usrEntityDto = mapper.convertValue(usrEntity, UserEntityDto.class);

					allUserEntityDtos.add(usrEntityDto);

				}
				userManagementResponse.setUserDetail(allUserEntityDtos);
			} else {
				throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(),
						"Role Code doesn't eixsts and Active.", userManagementRequest.getRoleCode());
			}

		} else {
			List<UserRoleEntityDto> allEntityDtos = new ArrayList<>();
			List<UserRoleMasterEntity> userRoleMasterEntities = userRoleRepo.findAll();

			for (UserRoleMasterEntity userRoleEnt : userRoleMasterEntities) {
				if (userRoleEnt != null && userRoleEnt.getIsActive().equals("Y")) {
					Long roleId = userRoleEnt.getId();
					List<UserRoleMapping> userRoleMappings = userRoleMappingRepo.findByRoleIdAndIsActive(roleId, "Y");
					if (!userRoleMappings.isEmpty()) {
						UserRoleEntityDto userRoleEntityDto = mapper.convertValue(userRoleEnt, UserRoleEntityDto.class);
						List<Long> ids = userRoleMappings.stream().map(UserRoleMapping::getUsersId)
								.collect(Collectors.toList());
						List<UserEntity> userEntities = userRepo.findByIdInAndIsActive(ids, "Y");
						List<UserEntityDto> userEntityDtos = new ArrayList<>();
						for (UserEntity userEntity : userEntities) {
							UserEntityDto userEntityDto = mapper.convertValue(userEntity, UserEntityDto.class);
							userEntityDtos.add(userEntityDto);

						}
						userRoleEntityDto.setUserEntityDtos(userEntityDtos);
						allEntityDtos.add(userRoleEntityDto);
					}

				} else {
					throw new BusinessException(StatusCodeEnum.BAD_CREDENTIALS.getCode(), "Role Code not Active.",
							userManagementRequest.getRoleCode());
				}

			}
			userManagementResponse.setUserRoles(allEntityDtos);

		}

		return userManagementResponse;
	}
}
