package com.jikim.wantedbackend.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jikim.wantedbackend.company.entity.Company;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

	List<Recruitment> findByCompany(Company company);

	List<Recruitment> findByPositionContains(String position);

	List<Recruitment> findByCompensationGreaterThanEqual(Integer compensation);

	List<Recruitment> findByTechnologyContains(String technology);
}
