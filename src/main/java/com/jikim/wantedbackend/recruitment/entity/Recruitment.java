package com.jikim.wantedbackend.recruitment.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jikim.wantedbackend.apply.entity.Apply;
import com.jikim.wantedbackend.company.entity.Company;
import com.jikim.wantedbackend.recruitment.dto.RecruitmentUpdateDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recruitment {

	@Id
	@Column(name = "recruitment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(name = "recruitment_position")
	@NotNull
	private String position;

	@Column(name = "recruitment_compensation")
	@NotNull
	private Integer compensation;

	@Column(name = "recruitment_content")
	@NotNull
	private String content;

	@Column(name = "recruitment_technology")
	@NotNull
	private String technology;

	@JsonIgnore
	@OneToMany(mappedBy = "recruitment")
	private Set<Apply> applies = new HashSet<>();

	public static Recruitment updateRecruitment(Recruitment recruitment, RecruitmentUpdateDto dto) {
		recruitment.position = dto.getPosition();
		recruitment.compensation = dto.getCompensation();
		recruitment.content = dto.getContent();
		recruitment.technology = dto.getTechnology();
		return recruitment;
	}

	@Override
	public String toString() {
		return "Recruitment{" +
			"id=" + id +
			", company=" + company +
			", position='" + position + '\'' +
			", compensation=" + compensation +
			", content='" + content + '\'' +
			", technology='" + technology + '\'' +
			'}';
	}
}
