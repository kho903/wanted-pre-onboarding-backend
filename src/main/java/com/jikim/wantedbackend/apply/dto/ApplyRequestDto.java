package com.jikim.wantedbackend.apply.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class ApplyRequestDto {
	@JsonProperty("recruitment_id")
	private Long recruitmentId;

	@JsonProperty("user_id")
	private Long userId;
}
