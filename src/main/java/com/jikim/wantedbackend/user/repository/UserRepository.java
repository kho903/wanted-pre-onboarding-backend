package com.jikim.wantedbackend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jikim.wantedbackend.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
