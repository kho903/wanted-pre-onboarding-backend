package com.jikim.wantedbackend;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DbSettingTest {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassWord;

	@Test
	void testConnection() {
		try(Connection connection =
				DriverManager.getConnection(
					dbUrl, dbUsername, dbPassWord
				)) {
			System.out.println("성공");
		} catch (SQLException e) {
			fail();
		}
	}
}
