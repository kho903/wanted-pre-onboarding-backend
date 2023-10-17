package com.jikim.wantedbackend.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jikim.wantedbackend.recruitment.Recruitment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentResponseDto {

	@JsonProperty("recruitment_id")
	private Long id;

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("company_country")
	private String companyCountry;

	@JsonProperty("company_location")
	private String companyLocation;

	@JsonProperty("recruitment_position")
	private String position;

	@JsonProperty("recruitment_compensation")
	private Integer compensation;

	@JsonProperty("recruitment_technology")
	private String technology;

	@Builder
	public RecruitmentResponseDto(Long id, String companyName, String companyCountry, String companyLocation,
		Integer compensation, String position, String technology) {
		this.id = id;
		this.companyName = companyName;
		this.companyCountry = companyCountry;
		this.companyLocation = companyLocation;
		this.position = position;
		this.compensation = compensation;
		this.technology = technology;
	}

	public static RecruitmentResponseDto toResponse(Recruitment recruitment) {
		return RecruitmentResponseDto.builder()
			.id(recruitment.getId())
			.companyName(recruitment.getCompany().getName())
			.companyCountry(recruitment.getCompany().getCountry())
			.companyLocation(recruitment.getCompany().getLocation())
			.position(recruitment.getPosition())
			.compensation(recruitment.getCompensation())
			.technology(recruitment.getTechnology())
			.build();
	}
}
