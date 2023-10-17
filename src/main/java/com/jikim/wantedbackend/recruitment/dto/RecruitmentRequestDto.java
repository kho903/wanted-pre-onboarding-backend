package com.jikim.wantedbackend.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecruitmentRequestDto {

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

	@Builder
	public RecruitmentRequestDto(Long companyId, String position, Integer compensation, String content,
		String technology) {
		this.companyId = companyId;
		this.position = position;
		this.compensation = compensation;
		this.content = content;
		this.technology = technology;
	}
}
