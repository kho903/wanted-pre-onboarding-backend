package com.jikim.wantedbackend.recruitment;

import org.springframework.stereotype.Service;

import com.jikim.wantedbackend.company.Company;
import com.jikim.wantedbackend.company.CompanyRepository;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentRequestDto;
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
}
