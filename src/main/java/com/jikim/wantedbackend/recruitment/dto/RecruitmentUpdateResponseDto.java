package com.jikim.wantedbackend.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jikim.wantedbackend.recruitment.Recruitment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentUpdateResponseDto {

	@JsonProperty("recruitment_id")
	private Long id;

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
	public RecruitmentUpdateResponseDto(Long id, Long companyId, String position, Integer compensation, String content,
		String technology) {
		this.id = id;
		this.companyId = companyId;
		this.position = position;
		this.compensation = compensation;
		this.content = content;
		this.technology = technology;
	}

	public static RecruitmentUpdateResponseDto toResponse(Recruitment recruitment) {
		return RecruitmentUpdateResponseDto.builder()
			.id(recruitment.getId())
			.companyId(recruitment.getCompany().getId())
			.position(recruitment.getPosition())
			.compensation(recruitment.getCompensation())
			.content(recruitment.getContent())
			.technology(recruitment.getTechnology())
			.build();
	}
}
