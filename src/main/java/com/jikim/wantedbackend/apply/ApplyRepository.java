package com.jikim.wantedbackend.apply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

	boolean existsByUserIdAndRecruitmentId(Long userId, Long recruitmentId);
}
