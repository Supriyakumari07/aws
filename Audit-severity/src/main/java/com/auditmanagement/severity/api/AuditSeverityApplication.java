package com.auditmanagement.severity.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.auditmanagement.severity.api.repo" })
@EntityScan(basePackages = { "com.auditmanagement.severity.api.model" })
@EnableFeignClients(basePackages = { "com.auditmanagement.severity.api.feign" })

@SpringBootApplication
public class AuditSeverityApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuditSeverityApplication.class, args);
	}

}
