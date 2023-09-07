package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zee.ticket.system.entity.UserRoleMapping;

public interface UserRoleMappingRepo extends JpaRepository<UserRoleMapping, Long> {

	List<UserRoleMapping> findByRoleIdAndIsActive(Long roleId, String isActive);

}
