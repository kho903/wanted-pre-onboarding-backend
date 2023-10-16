package com.jikim.wantedbackend.recruitment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.jikim.wantedbackend.company.Company;

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
