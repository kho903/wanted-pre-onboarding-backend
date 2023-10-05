package com.jikim.wantedbackend.recruitment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Recruitment {

	@Id
	@Column(name = "recruitment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "country")
	private String country;

	@Column(name = "location")
	private String location;

	@Column(name = "recruitment_position")
	private String position;

	@Column(name = "recruitment_compensation")
	private Integer compensation;

	@Column(name = "recruitment_technology")
	private String technology;

}
