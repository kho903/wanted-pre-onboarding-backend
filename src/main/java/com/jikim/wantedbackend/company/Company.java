package com.jikim.wantedbackend.company;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.jikim.wantedbackend.recruitment.Recruitment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;

	@Column(name = "company_name")
	@NotNull
	private String name;

	@Column(name = "company_country")
	@NotNull
	private String country;

	@Column(name = "company_location")
	@NotNull
	private String location;

	@OneToMany(mappedBy = "company")
	private List<Recruitment> recruitments = new ArrayList<>();
}
