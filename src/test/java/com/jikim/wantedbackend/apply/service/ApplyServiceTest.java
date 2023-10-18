package com.jikim.wantedbackend.apply.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.jikim.wantedbackend.apply.entity.Apply;
import com.jikim.wantedbackend.apply.repository.ApplyRepository;
import com.jikim.wantedbackend.company.entity.Company;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;
import com.jikim.wantedbackend.recruitment.repository.RecruitmentRepository;
import com.jikim.wantedbackend.user.entity.User;
import com.jikim.wantedbackend.user.repository.UserRepository;

@Transactional
@ExtendWith(MockitoExtension.class)
class ApplyServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	RecruitmentRepository recruitmentRepository;

	@Mock
	ApplyRepository applyRepository;

	@InjectMocks
	ApplyService applyService;

	@Test
	void applyRecruitmentByUser_test() throws Exception {
	    // given
		Long userId = 1L;
		Long recruitmentId = 1L;


		User user = User.builder()
			.id(userId)
			.build();
		Company company = Company.builder()
			.id(1L)
			.name("원티드랩")
			.country("한국")
			.location("판교")
			.build();
		Recruitment recruitment = Recruitment.builder()
			.id(recruitmentId)
			.company(company)
			.position("backend")
			.compensation(100000)
			.content("주니어 백엔드 개발자 공고 ")
			.technology("java")
			.applies(new HashSet<>())
			.build();
		Apply apply = Apply.builder()
			.recruitment(recruitment)
			.user(user)
			.build();
		user.getApplies().add(apply);
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(recruitmentRepository.findById(recruitmentId)).thenReturn(Optional.of(recruitment));
		when(applyRepository.save(any())).thenReturn(apply);

		// when
		Apply returnApply = applyService.applyRecruitmentByUser(userId, recruitmentId);
		System.out.println(returnApply);

		// then
		assertThat(apply.getId()).isEqualTo(returnApply.getId());
		assertThat(apply.getRecruitment().getId()).isEqualTo(returnApply.getRecruitment().getId());
		assertThat(apply.getRecruitment().getCompany().getId()).isEqualTo(returnApply.getRecruitment().getCompany().getId());
		assertThat(apply.getRecruitment().getCompany().getName()).isEqualTo(returnApply.getRecruitment().getCompany().getName());
		assertThat(apply.getRecruitment().getCompany().getCountry()).isEqualTo(returnApply.getRecruitment().getCompany().getCountry());
		assertThat(apply.getRecruitment().getCompany().getLocation()).isEqualTo(returnApply.getRecruitment().getCompany().getLocation());
		assertThat(apply.getRecruitment().getPosition()).isEqualTo(returnApply.getRecruitment().getPosition());
		assertThat(apply.getRecruitment().getCompensation()).isEqualTo(returnApply.getRecruitment().getCompensation());
		assertThat(apply.getRecruitment().getContent()).isEqualTo(returnApply.getRecruitment().getContent());
		assertThat(apply.getRecruitment().getTechnology()).isEqualTo(returnApply.getRecruitment().getTechnology());
	}
}