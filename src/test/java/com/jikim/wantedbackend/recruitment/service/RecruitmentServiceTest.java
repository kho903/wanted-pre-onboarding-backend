package com.jikim.wantedbackend.recruitment.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.jikim.wantedbackend.company.entity.Company;
import com.jikim.wantedbackend.company.repository.CompanyRepository;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateDto;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;
import com.jikim.wantedbackend.recruitment.repository.RecruitmentRepository;

@Transactional
@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

	@Mock
	private RecruitmentRepository recruitmentRepository;

	@Mock
	private CompanyRepository companyRepository;

	@InjectMocks
	RecruitmentService recruitmentService;

	@Test
	void createRecruitment_test() {
		// given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		RecruitmentRequestDto recruitmentRequestDto = RecruitmentRequestDto.builder()
			.companyId(1L)
			.position("백엔드 주니어 개발자")
			.compensation(1500000)
			.content("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
			.technology("Python")
			.build();
		when(companyRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(company));

		Recruitment recruitment = Recruitment.builder()
			.company(company)
			.position(recruitmentRequestDto.getPosition())
			.compensation(recruitmentRequestDto.getCompensation())
			.content(recruitmentRequestDto.getContent())
			.technology(recruitmentRequestDto.getTechnology())
			.build();
		when(recruitmentRepository.save(Mockito.any(Recruitment.class))).thenReturn(recruitment);

		// when
		Recruitment targetRecruitment = recruitmentService.createRecruitment(recruitmentRequestDto);

		// then
		assertThat(targetRecruitment).isEqualTo(recruitment);
	}

	@Test
	void updateRecruitment_test() {
		// given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		Long recruitmentId = 1L;

		Recruitment recruitment = Recruitment.builder()
			.id(recruitmentId)
			.company(company)
			.position("백엔드 주니어 개발자")
			.compensation(1500000)
			.content("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
			.technology("Python")
			.build();

		when(recruitmentRepository.findById(recruitmentId)).thenReturn(Optional.of(recruitment));

		RecruitmentUpdateDto updateDto = RecruitmentUpdateDto.builder()
			.position("백엔드 시니어 개발자")
			.compensation(2000000)
			.content("원티드랩에서 백엔드 시니어 개발자를 채용합니다. 자격요건은 10년 이상 경력")
			.technology("Spring")
			.build();

		// when
		Recruitment updateRecruitment = recruitmentService.updateRecruitment(recruitmentId, updateDto);

		// then
		assertThat(updateRecruitment.getId()).isEqualTo(recruitment.getId());
		assertThat(updateRecruitment.getPosition()).isEqualTo(updateDto.getPosition());
		assertThat(updateRecruitment.getCompensation()).isEqualTo(updateDto.getCompensation());
		assertThat(updateRecruitment.getContent()).isEqualTo(updateDto.getContent());
		assertThat(updateRecruitment.getTechnology()).isEqualTo(updateDto.getTechnology());
	}

	@Test
	void deleteRecruitment_test() {
		// given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		RecruitmentRequestDto recruitmentRequestDto = RecruitmentRequestDto.builder()
			.companyId(1L)
			.position("백엔드 주니어 개발자")
			.compensation(1500000)
			.content("원티드랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..")
			.technology("Python")
			.build();
		when(companyRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(company));

		Long recruitmentId = 1L;
		Recruitment recruitment = Recruitment.builder()
			.id(recruitmentId)
			.company(company)
			.position(recruitmentRequestDto.getPosition())
			.compensation(recruitmentRequestDto.getCompensation())
			.content(recruitmentRequestDto.getContent())
			.technology(recruitmentRequestDto.getTechnology())
			.build();
		when(recruitmentRepository.save(Mockito.any(Recruitment.class))).thenReturn(recruitment);
		recruitmentService.createRecruitment(recruitmentRequestDto);

		// when
		recruitmentService.deleteRecruitment(recruitmentId);

		// then
		verify(recruitmentRepository, times(1)).deleteById(recruitmentId);
	}

	@Test
	void getRecruitments_test() {
	    // given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		List<Recruitment> recruitments = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Recruitment recruitment = Recruitment.builder()
				.id(Long.parseLong(i + ""))
				.company(company)
				.compensation(100000 * i)
				.position("backend")
				.content("주니어 백엔드 개발자 공고 " + i)
				.technology("java")
				.build();
			recruitments.add(recruitment);
		}
		List<RecruitmentResponseDto> recruitmentResponseDtos = recruitments.stream()
			.map(RecruitmentResponseDto::toResponse)
			.toList();
		when(recruitmentRepository.findAll()).thenReturn(recruitments);

	    // when
		List<RecruitmentResponseDto> responseDtos = recruitmentService.getRecruitments();

		// then
		for (int i = 0; i < 10; i++) {
			// TODO: equals, hashcode 를 override 하는 것이 맞을까. 아니면 이렇게 하는 것이 맞을까 고민
			// -> test 를 위해 필요 없을 것이라고 생각하는 것을 추가하는 것은 아닐 것이라고 현재 생각중.
			assertThat(recruitmentResponseDtos.get(i).getId()).isEqualTo(responseDtos.get(i).getId());
			assertThat(recruitmentResponseDtos.get(i).getCompanyName()).isEqualTo(responseDtos.get(i).getCompanyName());
			assertThat(recruitmentResponseDtos.get(i).getCompanyCountry()).isEqualTo(responseDtos.get(i).getCompanyCountry());
			assertThat(recruitmentResponseDtos.get(i).getCompanyLocation()).isEqualTo(responseDtos.get(i).getCompanyLocation());
			assertThat(recruitmentResponseDtos.get(i).getPosition()).isEqualTo(responseDtos.get(i).getPosition());
			assertThat(recruitmentResponseDtos.get(i).getCompensation()).isEqualTo(responseDtos.get(i).getCompensation());
			assertThat(recruitmentResponseDtos.get(i).getTechnology()).isEqualTo(responseDtos.get(i).getTechnology());
		}
	}
}