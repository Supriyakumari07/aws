package com.auditmanagement.severity.api.service;

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

import com.auditmanagement.severity.api.feign.AuditBenchmarkFeign;
import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditDetail;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.service.AuditResponseService;
import com.auditmanagement.severity.api.service.AuthorizationService;
import com.auditmanagement.severity.api.service.SeverityServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class SeverityServiceTest {

	@Mock
	AuditResponseService auditResponseService;

	@Mock
	AuthorizationService authorizationService;

	@Mock
	AuditBenchmarkFeign auditBenchmarkFeign;

	@InjectMocks
	SeverityServiceImpl severityService;

	@Test
	void testGenerateResponse() {

		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));

		List<AuditQuestions> questionResponseList = new ArrayList<>();
		questionResponseList.add(new AuditQuestions(1, 1, "Internal", "question", "Yes"));

		AuditRequest auditRequest = new AuditRequest("ProjectName", "ManagerName", "ApplicationOwnerName",
				new AuditDetail("Internal", new Date(), questionResponseList));

		AuditResponse auditResponse = new AuditResponse(1, "ProjectExecutionStatus", "RemedialActionDuration",
				"ManagerName", "ProjectName", new Date());

		when(severityService.getBenchmarkData("jwt")).thenReturn(benchmarkList);

		when(auditResponseService.getAuditResponse(benchmarkList, "Internal", questionResponseList))
				.thenReturn(auditResponse);

		when(auditResponseService.saveAuditResponse(auditResponse, auditRequest)).thenReturn(auditResponse);

		assertEquals(auditResponse, severityService.generateResponse(auditRequest, "jwt"));

	}

	@Test
	void testGetBenchmarkData() {
		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));

		when(severityService.getBenchmarkData("jwt")).thenReturn(benchmarkList);
		assertEquals(benchmarkList, severityService.getBenchmarkData("jwt"));
	}
}
