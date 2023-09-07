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
@Table(name = "stage_master")
public class StageEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long stageId;

	@Column(name = "stage_name")
	private String stageName;

	@Column(name = "stage_code")
	private String stageCode;

	@Column(name = "stage_desc")
	private String stageDesc;

	@Column(name = "create_date")
	private String createDate;

	@Column(name = "modify_date")
	private String modifyDate;

	@Column(name = "isActive")
	private String isActive;

}
