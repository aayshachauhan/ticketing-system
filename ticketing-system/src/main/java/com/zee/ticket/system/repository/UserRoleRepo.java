package com.zee.ticket.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zee.ticket.system.entity.UserRoleMasterEntity;

public interface UserRoleRepo extends JpaRepository<UserRoleMasterEntity, Long> {

	UserRoleMasterEntity findByRoleCodeAndIsActive(String roleCode, String isActive);

}
