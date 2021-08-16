package com.auditmanagement.severity.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auditmanagement.severity.api.feign.AuditBenchmarkFeign;
import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.model.AuditType;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeverityServiceImpl implements SeverityService {

	@Autowired
	private AuditBenchmarkFeign auditBenchmarkFeign;

	@Autowired
	private AuditResponseService auditResponseService;

	// this method is to create response
	@Override
	public AuditResponse generateResponse(AuditRequest request, String jwt) {
		AuditResponse response;

		// getting benchmark list from Benchmark-MS
		List<AuditBenchmark> benchmarkList = getBenchmarkData(jwt);

		log.debug("benchmark List retrieved");

		// if list is empty then it will return null response
		// and if any feignException occurs an empty response will be returned
		if (benchmarkList == null) {
			return null;
		} else if (benchmarkList.get(0).getBenchmarkId() == -1) {
			return new AuditResponse(-1, "", "", "", "", new Date());
		}

		log.info("No Exception found in benchmark List");

		var auditType = new AuditType();

		auditType.setType(request.getAuditDetail().getAuditType());

		// getting responses back from RequestBody
		List<AuditQuestions> questionResponses = request.getAuditDetail().getAuditQuestion();

		log.debug("questions retrieved from request Body");

		// create Audit-response
		response = auditResponseService.getAuditResponse(benchmarkList, auditType.getType(), questionResponses);

		// saving response in DB
		response = auditResponseService.saveAuditResponse(response, request);

		log.info("AuditResponse successfully returned!!");

		return response;
	}

	// This method will call Benchmark MS
	@Override
	public List<AuditBenchmark> getBenchmarkData(String jwt) {
		List<AuditBenchmark> benchmarkList = null;

		try {
			benchmarkList = auditBenchmarkFeign.getAuditBenchmark(jwt);
		} catch (FeignException e) {

			List<AuditBenchmark> list = new ArrayList<>();
			list.add(new AuditBenchmark(-1, "", -1));
			return list;
		}
		return benchmarkList;

	}

}
