package com.jikim.wantedbackend.recruitment;

import org.springframework.stereotype.Service;

import com.jikim.wantedbackend.company.Company;
import com.jikim.wantedbackend.company.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

	private final RecruitmentRepository recruitmentRepository;
	private final CompanyRepository companyRepository;

	public Recruitment createRecruitment(RecruitmentDto recruitmentDto) {
		Company company = companyRepository.findById(recruitmentDto.getCompanyId())
			.orElseThrow(() -> new RuntimeException("회사가 존재하지 않습니다."));

		Recruitment recruitment = Recruitment.builder()
			.company(company)
			.position(recruitmentDto.getPosition())
			.compensation(recruitmentDto.getCompensation())
			.content(recruitmentDto.getContent())
			.technology(recruitmentDto.getTechnology())
			.build();
		return recruitmentRepository.save(recruitment);
	}
}
