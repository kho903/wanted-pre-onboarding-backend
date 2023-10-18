package com.jikim.wantedbackend.recruitment.controller;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateDto;
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
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("company_id").value(requestDto.getCompanyId()))
			.andExpect(jsonPath("recruitment_position").value(requestDto.getPosition()))
			.andExpect(jsonPath("recruitment_compensation").value(requestDto.getCompensation()))
			.andExpect(jsonPath("recruitment_content").value(requestDto.getContent()))
			.andExpect(jsonPath("recruitment_technology").value(requestDto.getTechnology()))
			.andDo(print());
	}

	@Test
	void updateRecruitment_test() throws Exception {
		// given
		RecruitmentUpdateDto updateDto = RecruitmentUpdateDto.builder()
			.position("변경된 포지션")
			.compensation(1)
			.content("변경된 콘텐트")
			.technology("변경된 기술")
			.build();

		// when // then
		mockMvc.perform(MockMvcRequestBuilders.put("/recruitment/1")
				.contentType(APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("recruitment_position").value(updateDto.getPosition()))
			.andExpect(jsonPath("recruitment_compensation").value(updateDto.getCompensation()))
			.andExpect(jsonPath("recruitment_content").value(updateDto.getContent()))
			.andExpect(jsonPath("recruitment_technology").value(updateDto.getTechnology()))
			.andDo(print());
	}

	@Test
	void deleteRecruitment_test() throws Exception {
		// given
		Long deleteId = 1L;

		// when // then
		mockMvc.perform(MockMvcRequestBuilders.delete("/recruitment/{deleteId}",
				Integer.parseInt(String.valueOf(deleteId))))
			.andExpect(status().isOk());
	}

	/**
	 * TODO: 아래는 간단하게만 진행. (andExpect(status().isOk()))
	 * 현재 data.sql 로 미리 넣어 둔 데이터로 진행했으므로 후에 리팩토링 요망.
	 */
	@Test
	void getRecruitments_test() throws Exception {
		// given
		// when // then
		mockMvc.perform(MockMvcRequestBuilders.get("/recruitment"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void getRecruitments_search_test() throws Exception {
		// given
		// when // then
		mockMvc.perform(MockMvcRequestBuilders.get("/recruitment")
				.param("search", "원티드"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void getRecruitment_test() throws Exception {
		// given
		Long recruitmentId = 1L;

		// when // then
		mockMvc.perform(MockMvcRequestBuilders.get("/recruitment/{id}",
					Integer.parseInt(String.valueOf(recruitmentId))))
			.andExpect(status().isOk())
			.andDo(print());
	}
}