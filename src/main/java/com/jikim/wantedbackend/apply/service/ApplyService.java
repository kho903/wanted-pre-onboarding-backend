package com.jikim.wantedbackend.apply.service;

import org.springframework.stereotype.Service;

import com.jikim.wantedbackend.apply.entity.Apply;
import com.jikim.wantedbackend.apply.repository.ApplyRepository;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;
import com.jikim.wantedbackend.recruitment.repository.RecruitmentRepository;
import com.jikim.wantedbackend.user.entity.User;
import com.jikim.wantedbackend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplyService {

	private final ApplyRepository applyRepository;
	private final UserRepository userRepository;
	private final RecruitmentRepository recruitmentRepository;

	public Apply applyRecruitmentByUser(Long userId, Long recruitmentId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("해당하는 user가 존재하지 않습니다."));
		Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
			.orElseThrow(() -> new RuntimeException("해당하는 공고가 존재하지 않습니다."));

		boolean existInRecruitmentByUserid = applyRepository.existsByUserIdAndRecruitmentId(userId, recruitmentId);

		if (existInRecruitmentByUserid) {
			throw new RuntimeException("이미 지원한 공고입니다.");
		}
		else {
			Apply apply = Apply.builder()
				.recruitment(recruitment)
				.user(user)
				.build();
			recruitment.getApplies().add(apply);
			user.getApplies().add(apply);
			return applyRepository.save(apply);
		}
	}
}
