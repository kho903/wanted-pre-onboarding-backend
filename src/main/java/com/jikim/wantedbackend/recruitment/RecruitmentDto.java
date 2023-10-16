package com.jikim.wantedbackend.recruitment;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentDto {

	@JsonProperty("company_id")
	private Long companyId;

	@JsonProperty("recruitment_position")
	private String position;

	@JsonProperty("recruitment_compensation")
	private Integer compensation;

	@JsonProperty("recruitment_content")
	private String content;

	@JsonProperty("recruitment_technology")
	private String technology;
}
