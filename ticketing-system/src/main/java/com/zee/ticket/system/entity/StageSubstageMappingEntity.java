package com.zee.ticket.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stage_substage")
public class StageSubstageMappingEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stageSubstageMappingId;

	@Column(name = "stage_id")
	private Long stageId;

	@Column(name = "sub_stage_id")
	private Long subStageId;

	@Column(name = "is_active")
	private String isActive;

}
