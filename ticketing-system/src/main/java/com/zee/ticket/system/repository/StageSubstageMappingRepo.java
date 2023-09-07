package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.StageSubstageMappingEntity;

@Repository
public interface StageSubstageMappingRepo extends JpaRepository<StageSubstageMappingEntity, Long> {

	@Query(value = "Select * from stage_substage  where stage_id= :stageId and sub_stage_id = :substageId", nativeQuery = true)
	StageSubstageMappingEntity findByStageIdAndSubStageId(@Param("stageId") Long stageId,
			@Param("substageId") Long substageId);

	List<StageSubstageMappingEntity> findByStageIdAndIsActive(Long stageId, String string);

}
