package com.zee.ticket.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zee.ticket.system.entity.SubstageEntity;

@Repository
public interface SubstageRepo extends JpaRepository<SubstageEntity, Long> {

	SubstageEntity findBySubStageCode(String subStageCode);

	List<SubstageEntity> findBySubStageIdInAndIsActive(List<Long> subStageIds, String string);

}
