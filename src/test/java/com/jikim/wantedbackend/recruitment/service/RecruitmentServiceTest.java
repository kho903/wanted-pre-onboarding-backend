package com.jikim.wantedbackend.recruitment.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.jikim.wantedbackend.company.entity.Company;
import com.jikim.wantedbackend.company.repository.CompanyRepository;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentDetailResponseDto;
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

	@Test
	void searchRecruitments_location_test() {
		// given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		Company company2 = Company.builder()
			.id(2L)
			.name("원티드코리아")
			.country("한국")
			.location("부산")
			.build();

		Company company3 = Company.builder()
			.id(2L)
			.name("네이버")
			.country("한국")
			.location("판교")
			.build();

		when(companyRepository.findByNameContains("판교")).thenReturn(List.of(company, company3));
		when(companyRepository.findByNameContains("부산")).thenReturn(List.of(company2));

		List<Recruitment> recruitments1 = new ArrayList<>();
		List<Recruitment> recruitments2 = new ArrayList<>();
		List<Recruitment> recruitments3 = new ArrayList<>();
		List<Recruitment> busanRecruitments = new ArrayList<>();
		List<Recruitment> pangyoRecruitments = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Recruitment recruitment = Recruitment.builder()
				.id(Long.parseLong(i + ""))
				.company(i % 3 == 1 ? company : i % 3 == 0 ? company2 : company3)
				.compensation(100000 * i)
				.position("backend")
				.content("주니어 백엔드 개발자 공고 " + i)
				.technology("java")
				.build();

			if (i % 3 == 0) {
				busanRecruitments.add(recruitment);
				recruitments2.add(recruitment);
			} else if (i % 3 == 1){
				pangyoRecruitments.add(recruitment);
				recruitments1.add(recruitment);
			} else {
				pangyoRecruitments.add(recruitment);
				recruitments3.add(recruitment);
			}
		}
		List<RecruitmentResponseDto> busanRequestResponse = busanRecruitments.stream()
			.map(RecruitmentResponseDto::toResponse)
			.toList();
		List<RecruitmentResponseDto> pangyoRequestResponse = pangyoRecruitments.stream()
			.map(RecruitmentResponseDto::toResponse)
			.toList();

		when(recruitmentRepository.findByCompany(company)).thenReturn(recruitments1);
		when(recruitmentRepository.findByCompany(company2)).thenReturn(recruitments2);
		when(recruitmentRepository.findByCompany(company3)).thenReturn(recruitments3);

		// when
		List<RecruitmentResponseDto> responseBusan = recruitmentService.searchRecruitments("부산");
		List<RecruitmentResponseDto> responsePangyo = recruitmentService.searchRecruitments("판교");

		// then
		for (int i = 0; i < busanRequestResponse.size(); i++) {
			assertThat(busanRequestResponse.get(i).getId()).isEqualTo(responseBusan.get(i).getId());
			assertThat(busanRequestResponse.get(i).getCompanyName()).isEqualTo(responseBusan.get(i).getCompanyName());
			assertThat(busanRequestResponse.get(i).getCompanyCountry()).isEqualTo(responseBusan.get(i).getCompanyCountry());
			assertThat(busanRequestResponse.get(i).getCompanyLocation()).isEqualTo(responseBusan.get(i).getCompanyLocation());
			assertThat(busanRequestResponse.get(i).getPosition()).isEqualTo(responseBusan.get(i).getPosition());
			assertThat(busanRequestResponse.get(i).getCompensation()).isEqualTo(responseBusan.get(i).getCompensation());
			assertThat(busanRequestResponse.get(i).getTechnology()).isEqualTo(responseBusan.get(i).getTechnology());
		}

		for (int i = 0; i < pangyoRequestResponse.size(); i++) {
			assertThat(pangyoRequestResponse.get(i).getId()).isEqualTo(responsePangyo.get(i).getId());
			assertThat(pangyoRequestResponse.get(i).getCompanyName()).isEqualTo(responsePangyo.get(i).getCompanyName());
			assertThat(pangyoRequestResponse.get(i).getCompanyCountry()).isEqualTo(responsePangyo.get(i).getCompanyCountry());
			assertThat(pangyoRequestResponse.get(i).getCompanyLocation()).isEqualTo(responsePangyo.get(i).getCompanyLocation());
			assertThat(pangyoRequestResponse.get(i).getPosition()).isEqualTo(responsePangyo.get(i).getPosition());
			assertThat(pangyoRequestResponse.get(i).getCompensation()).isEqualTo(responsePangyo.get(i).getCompensation());
			assertThat(pangyoRequestResponse.get(i).getTechnology()).isEqualTo(responsePangyo.get(i).getTechnology());
		}
	}

	@Test
	void getRecruitment_test() throws Exception {
	    // given
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();

		Recruitment recruitment = Recruitment.builder()
			.id(1L)
			.company(company)
			.position("backend")
			.compensation(100000)
			.content("주니어 백엔드 개발자 공고 ")
			.technology("java")
			.build();

		Recruitment recruitment2 = Recruitment.builder()
			.id(2L)
			.company(company)
			.position("frontend")
			.compensation(100000)
			.content("주니어 프론트엔드 개발자 공고 ")
			.technology("js")
			.build();

		Recruitment recruitment3 = Recruitment.builder()
			.id(3L)
			.company(company)
			.position("devops")
			.compensation(100000)
			.content("시니어 데브옵스 개발자 공고 ")
			.technology("java")
			.build();

		when(recruitmentRepository.findById(1L)).thenReturn(Optional.of(recruitment));
		when(recruitmentRepository.findByCompany(company)).thenReturn(List.of(recruitment, recruitment2, recruitment3));
		RecruitmentDetailResponseDto detailResponseForTesting =
			RecruitmentDetailResponseDto.toResponse(recruitment);
		when(recruitmentRepository.findByCompany(recruitment.getCompany())).thenReturn(List.of(recruitment2, recruitment3));
		detailResponseForTesting.addRecruitmentOther(List.of(recruitment2.getId(), recruitment3.getId()));

		// when
		RecruitmentDetailResponseDto response = recruitmentService.getRecruitment(1L);

		// then
		assertThat(detailResponseForTesting.getId()).isEqualTo(response.getId());
		assertThat(detailResponseForTesting.getCompanyName()).isEqualTo(response.getCompanyName());
		assertThat(detailResponseForTesting.getCompanyCountry()).isEqualTo(response.getCompanyCountry());
		assertThat(detailResponseForTesting.getCompanyLocation()).isEqualTo(response.getCompanyLocation());
		assertThat(detailResponseForTesting.getPosition()).isEqualTo(response.getPosition());
		assertThat(detailResponseForTesting.getCompensation()).isEqualTo(response.getCompensation());
		assertThat(detailResponseForTesting.getTechnology()).isEqualTo(response.getTechnology());
		assertThat(detailResponseForTesting.getContent()).isEqualTo(response.getContent());
		assertThat(detailResponseForTesting.getRecruitmentOther().get(0)).isEqualTo(response.getRecruitmentOther().get(0));
		assertThat(detailResponseForTesting.getRecruitmentOther().get(1)).isEqualTo(response.getRecruitmentOther().get(1));
	}
}