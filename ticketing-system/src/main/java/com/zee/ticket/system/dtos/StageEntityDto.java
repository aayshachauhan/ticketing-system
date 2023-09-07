package com.zee.ticket.system.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageEntityDto {

	private Long stageId;

	private String stageName;

	private String stageCode;

	private String stageDesc;

	private String createDate;

	private String modifyDate;

	private String isActive;

	public List<SubstageEntityDto> substageCodes;

}
