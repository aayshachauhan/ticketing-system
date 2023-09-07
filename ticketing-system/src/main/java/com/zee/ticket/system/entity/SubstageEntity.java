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
@Table(name = "sub_stage_master")
public class SubstageEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subStageId;

	@Column(name = "sub_stage_name")
	private String subStageName;

	@Column(name = "sub_stage_code")
	private String subStageCode;

	@Column(name = "sub_stage_desc")
	private String subStageDesc;

	@Column(name = "create_date")
	private String createDate;

	@Column(name = "modify_date")
	private String modifyDate;

	@Column(name = "is_Active")
	private String isActive;

}
