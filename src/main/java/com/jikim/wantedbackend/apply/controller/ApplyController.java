package com.jikim.wantedbackend.apply.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jikim.wantedbackend.apply.dto.ApplyRequestDto;
import com.jikim.wantedbackend.apply.dto.ApplyResponseDto;
import com.jikim.wantedbackend.apply.entity.Apply;
import com.jikim.wantedbackend.apply.service.ApplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
public class ApplyController {

	private final ApplyService applyService;

	@PostMapping
	public ResponseEntity<ApplyResponseDto> createApply(@RequestBody ApplyRequestDto applyRequestDto) {
		Apply apply = applyService.applyRecruitmentByUser(applyRequestDto.getUserId(),
			applyRequestDto.getRecruitmentId());
		ApplyResponseDto responseDto = ApplyResponseDto.builder()
			.id(apply.getId())
			.recruitmentId(apply.getRecruitment().getId())
			.userId(apply.getUser().getId())
			.build();
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
}
