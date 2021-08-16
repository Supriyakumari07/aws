package com.auditmanagement.severity.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.auditmanagement.severity.api.controller.SeverityController;
import com.auditmanagement.severity.api.exception.BenchmarkException;
import com.auditmanagement.severity.api.exception.FeignProxyException;
import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditDetail;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.service.AuthorizationService;
import com.auditmanagement.severity.api.service.SeverityService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class SeverityControllerTest {

	@Mock
	AuthorizationService authorizationService;

	@Mock
	SeverityService severityService;

	@InjectMocks
	SeverityController severityController;

	@Test
	void testSeverityController() {
		assertThat(severityController).isNotNull();
	}

	@Test
	void testAuditSeverityStatus() throws BenchmarkException, FeignProxyException {
		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));

		List<AuditQuestions> questionResponseList = new ArrayList<>();
		questionResponseList.add(new AuditQuestions(1, 1, "Internal", "question", "Yes"));

		AuditRequest auditRequest = new AuditRequest("ProjectName", "ManagerName", "ApplicationOwnerName",
				new AuditDetail("Internal", new Date(), questionResponseList));

		AuditResponse auditResponse = new AuditResponse(1, "ProjectExecutionStatus", "RemedialActionDuration",
				"ManagerName", "ProjectName", new Date());

		when(authorizationService.validateJwt("jwt")).thenReturn(true);

		when(severityService.generateResponse(auditRequest, "jwt")).thenReturn(auditResponse);

		ResponseEntity<AuditResponse> expectedResponse = new ResponseEntity<AuditResponse>(auditResponse,
				HttpStatus.OK);

		assertEquals(expectedResponse, severityController.auditSeverity("jwt", auditRequest));

	}
}
