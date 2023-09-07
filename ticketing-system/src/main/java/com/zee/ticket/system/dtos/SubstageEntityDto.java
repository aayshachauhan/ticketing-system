package com.zee.ticket.system.dtos;

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
public class SubstageEntityDto {

	private String subStageId;

	private String subStageName;

	private String subStageCode;

	private String subStageDesc;

	private String createDate;

	private String modifyDate;

	private String isActive;

}
