package com.jikim.wantedbackend.recruitment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruitment")
@Log4j2
public class RecruitmentController {

	private final RecruitmentService recruitmentService;

	@PostMapping
	public ResponseEntity<Recruitment> createRecruitment(@RequestBody RecruitmentDto recruitmentDto) {
		Recruitment recruitment = recruitmentService.createRecruitment(recruitmentDto);
		log.info("RecruitmentController.createRecruitment={}", recruitment);
		return new ResponseEntity<>(recruitment, HttpStatus.CREATED);
	}
}
