package com.jikim.wantedbackend.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class JasyptConfig {
	@Value("${jasypt.encryptor.algorithm}")
	private String algorithm;
	@Value("${jasypt.encryptor.pool-size}")
	private int poolSize;
	@Value("${jasypt.encryptor.string-output-type}")
	private String stringOutputType;
	@Value("${jasypt.encryptor.key-obtention-iterations}")
	private int keyObtentionIterations;

	public JasyptConfig() {
	}

	@Bean
	public StringEncryptor jasyptStringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		encryptor.setPoolSize(poolSize);
		encryptor.setAlgorithm(algorithm);
		encryptor.setPassword(getJasyptEncryptorPassword());
		encryptor.setStringOutputType(stringOutputType);
		encryptor.setKeyObtentionIterations(keyObtentionIterations);
		String source = "암호화할 대상";
		log.info("plane :: {}, encrypt :: {}", source, encryptor.encrypt(source));
		return encryptor;
	}

	private String getJasyptEncryptorPassword() {
		try {
			ClassPathResource resource = new ClassPathResource("password.txt");
			return String.join("", Files.readAllLines(Paths.get(resource.getURI())));
		} catch (IOException e) {
			throw new RuntimeException("Not found Jasypt password file.");
		}
	}
}
