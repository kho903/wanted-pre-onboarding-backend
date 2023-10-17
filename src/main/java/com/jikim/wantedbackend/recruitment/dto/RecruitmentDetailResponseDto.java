package com.jikim.wantedbackend.recruitment.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RecruitmentDetailResponseDto {

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

	@JsonProperty("recruitmentContent")
	private String content;

	@JsonProperty("recruitment_other")
	private List<Long> recruitmentOther = new ArrayList<>();

	@Builder
	public RecruitmentDetailResponseDto(Long id, String companyName, String companyCountry, String companyLocation,
		String position, Integer compensation, String technology, String content) {
		this.id = id;
		this.companyName = companyName;
		this.companyCountry = companyCountry;
		this.companyLocation = companyLocation;
		this.position = position;
		this.compensation = compensation;
		this.technology = technology;
		this.content = content;
	}

	public static RecruitmentDetailResponseDto toResponse(Recruitment recruitment) {
		return RecruitmentDetailResponseDto.builder()
			.id(recruitment.getId())
			.companyName(recruitment.getCompany().getName())
			.companyCountry(recruitment.getCompany().getCountry())
			.companyLocation(recruitment.getCompany().getLocation())
			.position(recruitment.getPosition())
			.compensation(recruitment.getCompensation())
			.technology(recruitment.getTechnology())
			.content(recruitment.getContent())
			.build();
	}

	public void addRecruitmentOther(List<Long> ids) {
		this.recruitmentOther.addAll(ids);
	}
}
