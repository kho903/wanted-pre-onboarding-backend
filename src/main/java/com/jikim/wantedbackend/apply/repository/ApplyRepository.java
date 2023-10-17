package com.jikim.wantedbackend.apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jikim.wantedbackend.apply.entity.Apply;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

	boolean existsByUserIdAndRecruitmentId(Long userId, Long recruitmentId);
}
