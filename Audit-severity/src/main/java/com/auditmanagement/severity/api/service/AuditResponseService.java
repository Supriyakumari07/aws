package com.auditmanagement.severity.api.service;

import java.util.List;

import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;

public interface AuditResponseService {
	public AuditResponse getAuditResponse(List<AuditBenchmark> benchmark, String auditType,
			List<AuditQuestions> questions);

	public AuditResponse saveAuditResponse(AuditResponse response, AuditRequest request);

}
