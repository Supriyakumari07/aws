package com.auditmanagement.severity.api.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.repo.ResponseRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuditResponseServiceImpl implements AuditResponseService {

	@Autowired
	private ResponseRepo responseRepo;

	@Override
	public AuditResponse getAuditResponse(List<AuditBenchmark> list, String auditType, List<AuditQuestions> questions) {
		var maxNos = 0;
		for (AuditBenchmark benchmark : list) {
			if (benchmark.getAuditType().equalsIgnoreCase(auditType)) {
				maxNos = benchmark.getBenchmarkNoAnswers();
			}

		}
		return generateAuditResponse(maxNos, questions);

	}

	@Override
	public AuditResponse saveAuditResponse(AuditResponse response, AuditRequest request) {
		// setting project name and manager name
		response.setProjectName(request.getProjectName());
		response.setManagerName(request.getProjectManagerName());
		response.setCreationDateTime(new Date());

		log.info("Saving Audit-Response in DB :: " + response);

		return responseRepo.save(response);
	}

	// This method is to check the audit responses with the audit type
	public AuditResponse generateAuditResponse(int maxNos, List<AuditQuestions> questions) {

		String type = questions.get(0).getAuditType();

		int noOfNos = count(questions);

		var response = new AuditResponse();

		log.info(String.format("Audit-type : %s, Actual-Nos : %d, Acceptable Nos : %d", type, noOfNos, maxNos));

		/*
		 * Here we check actual condition of the severity microservice Determines the
		 * project execution status and the remediation duration detail
		 */
		if (noOfNos <= maxNos && type.equalsIgnoreCase("Internal")) {
			response.setProjectExecutionStatus("Green");
			response.setRemedialActionDuration("No Action Needed");
		}

		else if (noOfNos >= maxNos && type.equalsIgnoreCase("Internal")) {
			response.setProjectExecutionStatus("Red");
			response.setRemedialActionDuration("Action to be taken in 2 weeks");
		}

		else if (noOfNos <= maxNos && type.equalsIgnoreCase("SOX")) {
			response.setProjectExecutionStatus("Green");
			response.setRemedialActionDuration("No Action Needed");
		}

		else if (noOfNos > maxNos && type.equalsIgnoreCase("SOX")) {
			response.setProjectExecutionStatus("Red");
			response.setRemedialActionDuration("Action to be taken in 1 weeks");
		}

		return response;

	}

	// This method is to count the number of No's
	public int count(List<AuditQuestions> questions) {
		var count = 0;
		for (AuditQuestions ques : questions) {
			if (ques.getResponse().equalsIgnoreCase("No")) {
				count++;
			}
		}
		return count;
	}

}
