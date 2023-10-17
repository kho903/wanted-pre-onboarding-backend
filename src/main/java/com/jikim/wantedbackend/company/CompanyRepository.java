package com.jikim.wantedbackend.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByNameContains(String name);

	List<Company> findByCountryContains(String country);

	List<Company> findByLocationContains(String location);
}
