package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zee.ticket.system.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

	List<UserEntity> findByIdInAndIsActive(List<Long> ids, String isActive);


}
