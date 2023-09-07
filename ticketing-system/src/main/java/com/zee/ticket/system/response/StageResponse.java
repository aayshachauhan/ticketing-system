package com.zee.ticket.system.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zee.ticket.system.dtos.StageEntityDto;
import com.zee.ticket.system.dtos.SubstageEntityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StageResponse {

	public List<StageEntityDto> stageEntityDtos;

	public List<SubstageEntityDto> substageEntityDtos;

}
