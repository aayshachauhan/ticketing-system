package com.zee.ticket.system.service;

import java.io.IOException;

import com.zee.ticket.system.request.UserManagementRequest;
import com.zee.ticket.system.response.UserManagementResponse;

public interface UserService {

	UserManagementResponse readByRoleCodes(UserManagementRequest userManagementRequest) throws Exception, IOException;

}
