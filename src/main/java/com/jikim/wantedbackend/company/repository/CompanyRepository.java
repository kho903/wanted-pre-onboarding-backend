package com.jikim.wantedbackend.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jikim.wantedbackend.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByNameContains(String name);

	List<Company> findByCountryContains(String country);

	List<Company> findByLocationContains(String location);
}
