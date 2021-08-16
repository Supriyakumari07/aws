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

import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditDetail;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.repo.ResponseRepo;
import com.auditmanagement.severity.api.service.AuditResponseServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AuditResponseServiceTest {

	@Mock
	ResponseRepo responseRepo;

	@InjectMocks
	AuditResponseServiceImpl auditResponseServiceImpl;

	AuditResponse auditResponse = new AuditResponse(1, "ProjectExecutionStatus", "RemedialActionDuration",
			"ManagerName", "ProjectName", new Date());

	List<AuditQuestions> questions = new ArrayList<>();

	List<AuditQuestions> questionResponseList = new ArrayList<>();

	AuditRequest auditRequest = new AuditRequest("ProjectName", "ManagerName", "ApplicationOwnerName",
			new AuditDetail("Internal", new Date(), questionResponseList));

	@Test
	void contextLoads() throws Exception {
		assertNotNull(auditResponseServiceImpl);
	}

	@Test
	void testGetAuditResponseInternal3Nos() {

		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));
		benchmarkList.add(new AuditBenchmark(2, "SOX", 1));

		List<AuditQuestions> questions = new ArrayList<>();
		questions.add(new AuditQuestions(1, 1, "Internal", "Have all Change requests followed SDLC before PROD move?",
				"Yes"));
		questions.add(new AuditQuestions(2, 2, "Internal",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "Internal",
				"Are all artifacts like CR document, Unit test cases available?", "No"));
		questions.add(new AuditQuestions(4, 4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "Internal",
				"Is data deletion from the system done with application owner approval?", "No"));
		auditResponse = new AuditResponse(0, "Green", "No Action Needed", null, null, null);

		assertEquals(auditResponse, auditResponseServiceImpl.getAuditResponse(benchmarkList, "Internal", questions));
	}

	@Test
	void testGetAuditResponseInternal4Nos() {

		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));
		benchmarkList.add(new AuditBenchmark(2, "SOX", 1));

		List<AuditQuestions> questions = new ArrayList<>();
		questions.add(new AuditQuestions(1, 1, "Internal", "Have all Change requests followed SDLC before PROD move?",
				"Yes"));
		questions.add(new AuditQuestions(2, 2, "Internal",
				"Have all Change requests been approved by the application owner?", "No"));
		questions.add(new AuditQuestions(3, 3, "Internal",
				"Are all artifacts like CR document, Unit test cases available?", "No"));
		questions.add(new AuditQuestions(4, 4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "Internal",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Red", "Action to be taken in 2 weeks", null, null, null);

		assertEquals(auditResponse, auditResponseServiceImpl.getAuditResponse(benchmarkList, "Internal", questions));
	}

	@Test
	void testGetAuditResponseSox1Nos() {

		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));
		benchmarkList.add(new AuditBenchmark(2, "SOX", 1));

		List<AuditQuestions> questions = new ArrayList<>();
		questions.add(
				new AuditQuestions(1, 1, "SOX", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		questions.add(new AuditQuestions(2, 2, "SOX",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "SOX", "Are all artifacts like CR document, Unit test cases available?",
				"Yes"));
		questions.add(new AuditQuestions(4, 4, "SOX", "Is the SIT and UAT sign-off available?", "Yes"));
		questions.add(new AuditQuestions(5, 5, "SOX",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Green", "No Action Needed", null, null, null);

		assertEquals(auditResponse, auditResponseServiceImpl.getAuditResponse(benchmarkList, "SOX", questions));
	}

	@Test
	void testGetAuditResponseSox4Nos() {

		List<AuditBenchmark> benchmarkList = new ArrayList<>();
		benchmarkList.add(new AuditBenchmark(1, "Internal", 3));
		benchmarkList.add(new AuditBenchmark(2, "SOX", 1));

		List<AuditQuestions> questions = new ArrayList<>();
		questions.add(
				new AuditQuestions(1, 1, "SOX", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		questions.add(new AuditQuestions(2, 2, "SOX",
				"Have all Change requests been approved by the application owner?", "No"));
		questions.add(new AuditQuestions(3, 3, "SOX", "Are all artifacts like CR document, Unit test cases available?",
				"No"));
		questions.add(new AuditQuestions(4, 4, "SOX", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "SOX",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Red", "Action to be taken in 1 weeks", null, null, null);

		assertEquals(auditResponse, auditResponseServiceImpl.getAuditResponse(benchmarkList, "SOX", questions));
	}

	@Test
	void testSaveAuditResponse() {
		questionResponseList.add(new AuditQuestions(1, 1, "Internal", "question", "YES"));
		when(responseRepo.save(auditResponse)).thenReturn(auditResponse);
		assertEquals(auditResponse, auditResponseServiceImpl.saveAuditResponse(auditResponse, auditRequest));
	}

	@Test
	void testCountNos() {
		questions.add(
				new AuditQuestions(1, 1, "SOX", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		questions.add(new AuditQuestions(2, 2, "SOX",
				"Have all Change requests been approved by the application owner?", "No"));
		questions.add(new AuditQuestions(3, 3, "SOX", "Are all artifacts like CR document, Unit test cases available?",
				"No"));
		questions.add(new AuditQuestions(4, 4, "SOX", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "SOX",
				"Is data deletion from the system done with application owner approval?", "No"));
		int count = 4;
		assertEquals(count, auditResponseServiceImpl.count(questions));
	}

	@Test
	void testCreateAuditResponseInternalGreenStatus() {
		questions.add(new AuditQuestions(1, 1, "Internal", "Have all Change requests followed SDLC before PROD move?",
				"Yes"));
		questions.add(new AuditQuestions(2, 2, "Internal",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "Internal",
				"Are all artifacts like CR document, Unit test cases available?", "No"));
		questions.add(new AuditQuestions(4, 4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "Internal",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Green", "No Action Needed", null, null, null);
		assertEquals(auditResponse, auditResponseServiceImpl.generateAuditResponse(3, questions));

	}

	@Test
	void testCreateAuditResponseInternalGreenStatusWithHighValue() {
		questions.add(new AuditQuestions(1, 1, "Internal", "Have all Change requests followed SDLC before PROD move?",
				"Yes"));
		questions.add(new AuditQuestions(2, 2, "Internal",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "Internal",
				"Are all artifacts like CR document, Unit test cases available?", "No"));
		questions.add(new AuditQuestions(4, 4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "Internal",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Green", "No Action Needed", null, null, null);
		assertEquals(auditResponse, auditResponseServiceImpl.generateAuditResponse(4, questions));

	}

	@Test
	void testCreateAuditResponseInternalRedStatus() {
		questions.add(new AuditQuestions(1, 1, "Internal", "Have all Change requests followed SDLC before PROD move?",
				"Yes"));
		questions.add(new AuditQuestions(2, 2, "Internal",
				"Have all Change requests been approved by the application owner?", "No"));
		questions.add(new AuditQuestions(3, 3, "Internal",
				"Are all artifacts like CR document, Unit test cases available?", "No"));
		questions.add(new AuditQuestions(4, 4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "Internal",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Red", "Action to be taken in 2 weeks", null, null, null);
		assertEquals(auditResponse, auditResponseServiceImpl.generateAuditResponse(3, questions));

	}

	@Test
	void testCreateAuditResponseSoxGreenStatus() {
		questions.add(
				new AuditQuestions(1, 1, "SOX", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		questions.add(new AuditQuestions(2, 2, "SOX",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "SOX", "Are all artifacts like CR document, Unit test cases available?",
				"Yes"));
		questions.add(new AuditQuestions(4, 4, "SOX", "Is the SIT and UAT sign-off available?", "Yes"));
		questions.add(new AuditQuestions(5, 5, "SOX",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Green", "No Action Needed", null, null, null);
		assertEquals(auditResponse, auditResponseServiceImpl.generateAuditResponse(1, questions));

	}

	@Test
	void testCreateAuditResponseSoxRedStatus() {
		questions.add(
				new AuditQuestions(1, 1, "SOX", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		questions.add(new AuditQuestions(2, 2, "SOX",
				"Have all Change requests been approved by the application owner?", "Yes"));
		questions.add(new AuditQuestions(3, 3, "SOX", "Are all artifacts like CR document, Unit test cases available?",
				"Yes"));
		questions.add(new AuditQuestions(4, 4, "SOX", "Is the SIT and UAT sign-off available?", "No"));
		questions.add(new AuditQuestions(5, 5, "SOX",
				"Is data deletion from the system done with application owner approval?", "No"));

		auditResponse = new AuditResponse(0, "Red", "Action to be taken in 1 weeks", null, null, null);
		assertEquals(auditResponse, auditResponseServiceImpl.generateAuditResponse(1, questions));

	}

}
