package com.jikim.wantedbackend.recruitment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jikim.wantedbackend.company.Company;
import com.jikim.wantedbackend.company.CompanyRepository;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentDetailResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentResponseDto;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

	private final RecruitmentRepository recruitmentRepository;
	private final CompanyRepository companyRepository;

	public Recruitment createRecruitment(RecruitmentRequestDto recruitmentRequestDto) {

		Company company = companyRepository.findById(recruitmentRequestDto.getCompanyId())
			.orElseThrow(() -> new RuntimeException("회사가 존재하지 않습니다."));

		Recruitment recruitment = Recruitment.builder()
			.company(company)
			.position(recruitmentRequestDto.getPosition())
			.compensation(recruitmentRequestDto.getCompensation())
			.content(recruitmentRequestDto.getContent())
			.technology(recruitmentRequestDto.getTechnology())
			.build();

		return recruitmentRepository.save(recruitment);
	}

	public Recruitment updateRecruitment(Long recruitmentId, RecruitmentUpdateDto updateDto) {

		Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
			.orElseThrow(() -> new RuntimeException("채용공고가 존재하지 않습니다."));

		return Recruitment.updateRecruitment(recruitment, updateDto);
	}

	public void deleteRecruitment(Long recruitmentId) {
		recruitmentRepository.deleteById(recruitmentId);
	}

	public List<RecruitmentResponseDto> getRecruitments() {
		List<Recruitment> recruitments = recruitmentRepository.findAll();
		return recruitments.stream()
			.map(RecruitmentResponseDto::toResponse).toList();
	}

	public List<RecruitmentResponseDto> searchRecruitments(String search) {
		Set<Recruitment> recruitmentSet = new HashSet<>();

		if (search.chars().allMatch(Character::isDigit)) {
			int compensation = Integer.parseInt(search);
			List<Recruitment> findCompensation = recruitmentRepository.findByCompensationGreaterThanEqual(
				compensation);
			recruitmentSet.addAll(findCompensation);
		} else {
			Set<Company> companies = new HashSet<>();
			companies.addAll(companyRepository.findByNameContains(search));
			companies.addAll(companyRepository.findByCountryContains(search));
			companies.addAll(companyRepository.findByLocationContains(search));

			for (Company company : companies) {
				recruitmentSet.addAll(recruitmentRepository.findByCompany(company));
			}
			recruitmentSet.addAll(recruitmentRepository.findByPositionContains(search));
			recruitmentSet.addAll(recruitmentRepository.findByTechnologyContains(search));
		}

		List<Recruitment> recruitments = recruitmentSet.stream()
				.sorted(Comparator.comparingLong(Recruitment::getId)).toList();
		return recruitments.stream()
			.map(RecruitmentResponseDto::toResponse)
			.toList();
	}

	public RecruitmentDetailResponseDto getRecruitment(Long id) {
		Recruitment recruitment = recruitmentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 채용공고가 존재하지 않습니다."));

		RecruitmentDetailResponseDto response =
			RecruitmentDetailResponseDto.toResponse(recruitment);
		List<Recruitment> otherRecruitment = recruitmentRepository.findByCompany(recruitment.getCompany());
		response.addRecruitmentOther(otherRecruitment.stream().map(Recruitment::getId).filter(recruitmentId -> recruitmentId != id).collect(Collectors.toList()));
		return response;
	}
}
