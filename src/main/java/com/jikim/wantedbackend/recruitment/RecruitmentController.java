package com.jikim.wantedbackend.recruitment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
@Log4j2
public class RecruitmentController {

	private final RecruitmentService recruitmentService;

	@PostMapping
	public ResponseEntity<RecruitmentResponseDto> createRecruitment(@RequestBody RecruitmentRequestDto recruitmentRequestDto) {
		Recruitment recruitment = recruitmentService.createRecruitment(recruitmentRequestDto);
		RecruitmentResponseDto response = RecruitmentResponseDto.toResponse(recruitment);
		log.info("RecruitmentController.createRecruitment={}", response);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RecruitmentResponseDto> updateRecruitment(@PathVariable Long id,
		@RequestBody RecruitmentUpdateDto recruitmentUpdateDto) {
		Recruitment recruitment = recruitmentService.updateRecruitment(id, recruitmentUpdateDto);
		RecruitmentResponseDto response = RecruitmentResponseDto.toResponse(recruitment);
		log.info("RecruitmentController.updateRecruitment={}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
