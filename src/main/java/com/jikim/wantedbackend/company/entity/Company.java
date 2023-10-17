package com.jikim.wantedbackend.company.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jikim.wantedbackend.recruitment.entity.Recruitment;

import lombok.Builder;
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
	@JsonIgnore
	private List<Recruitment> recruitments = new ArrayList<>();

	@Builder
	public Company(Long id, String name, String country, String location) {
		this.id = id;
		this.name = name;
		this.country = country;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Company{" +
			"id=" + id +
			", name='" + name + '\'' +
			", country='" + country + '\'' +
			", location='" + location + '\'' +
			'}';
	}
}
