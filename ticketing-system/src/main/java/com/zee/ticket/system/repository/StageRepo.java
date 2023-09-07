package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.StageEntity;

@Repository
public interface StageRepo extends JpaRepository<StageEntity, Long> {

	StageEntity findByStageCode(String stageCode);

	StageEntity findByStageCodeAndIsActive(String stageCode, String string);

}
