package com.auditmanagement.severity.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auditmanagement.severity.api.AuditSeverityApplication;

@SpringBootTest
class AuditSeverityApplicationTests {

	@Autowired
	AuditSeverityApplication auditSeverityApplication;

	@Test
	void contextLoads() {
		assertNotNull(auditSeverityApplication);
	}

}
