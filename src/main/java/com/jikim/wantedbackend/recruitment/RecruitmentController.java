package com.jikim.wantedbackend.recruitment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jikim.wantedbackend.recruitment.dto.RecruitmentDetailResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateResponseDto;
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
	public ResponseEntity<RecruitmentUpdateResponseDto> createRecruitment(@RequestBody RecruitmentRequestDto recruitmentRequestDto) {
		Recruitment recruitment = recruitmentService.createRecruitment(recruitmentRequestDto);
		RecruitmentUpdateResponseDto response = RecruitmentUpdateResponseDto.toResponse(recruitment);
		log.info("RecruitmentController.createRecruitment={}", response);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RecruitmentUpdateResponseDto> updateRecruitment(@PathVariable Long id,
		@RequestBody RecruitmentUpdateDto recruitmentUpdateDto) {
		Recruitment recruitment = recruitmentService.updateRecruitment(id, recruitmentUpdateDto);
		RecruitmentUpdateResponseDto response = RecruitmentUpdateResponseDto.toResponse(recruitment);
		log.info("RecruitmentController.updateRecruitment={}", response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> updateRecruitment(@PathVariable Long id) {
		recruitmentService.deleteRecruitment(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<RecruitmentResponseDto>> getRecruitments(@RequestParam(value = "search", required = false) String search) {
		if (search == null) return new ResponseEntity<>(recruitmentService.getRecruitments(), HttpStatus.OK);
		return new ResponseEntity<>(recruitmentService.searchRecruitments(search), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecruitmentDetailResponseDto> getRecruitment(@PathVariable Long id) {
		return new ResponseEntity<>(recruitmentService.getRecruitment(id), HttpStatus.OK);
	}
}
