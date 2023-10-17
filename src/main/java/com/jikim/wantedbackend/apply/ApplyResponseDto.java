package com.jikim.wantedbackend.apply;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplyResponseDto {

	@JsonProperty("apply_id")
	private Long id;

	@JsonProperty("recruitment_id")
	private Long recruitmentId;

	@JsonProperty("user_id")
	private Long userId;

	@Builder
	public ApplyResponseDto(Long id, Long recruitmentId, Long userId) {
		this.id = id;
		this.recruitmentId = recruitmentId;
		this.userId = userId;
	}
}
