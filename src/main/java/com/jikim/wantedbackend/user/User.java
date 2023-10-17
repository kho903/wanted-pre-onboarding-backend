package com.jikim.wantedbackend.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jikim.wantedbackend.apply.Apply;

import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id")
	private Long id;

	@OneToMany(mappedBy = "user")
	private List<Apply> applies = new ArrayList<>();

}
