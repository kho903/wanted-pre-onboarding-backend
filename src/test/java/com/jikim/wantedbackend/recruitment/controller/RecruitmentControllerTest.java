package com.jikim.wantedbackend.recruitment.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.service.RecruitmentService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RecruitmentControllerTest {

	@Autowired
	RecruitmentController recruitmentController;

	@Autowired
	RecruitmentService recruitmentService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void createRecruitment_test() throws Exception {
		// given
		RecruitmentRequestDto requestDto = RecruitmentRequestDto.builder()
			.companyId(1L)
			.position("백엔드 주니어 개발자")
			.compensation(1500000)
			.content("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
			.technology("Python")
			.build();

		// when // then
		mockMvc.perform(MockMvcRequestBuilders.post("/recruitment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("company_id").value(requestDto.getCompanyId()))
			.andExpect(jsonPath("recruitment_position").value(requestDto.getPosition()))
			.andExpect(jsonPath("recruitment_compensation").value(requestDto.getCompensation()))
			.andExpect(jsonPath("recruitment_content").value(requestDto.getContent()))
			.andExpect(jsonPath("recruitment_technology").value(requestDto.getTechnology()))
			.andDo(print());
	}
}