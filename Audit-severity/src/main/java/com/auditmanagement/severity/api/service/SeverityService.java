package com.auditmanagement.severity.api.service;

import java.util.List;

import com.auditmanagement.severity.api.model.AuditBenchmark;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;


public interface SeverityService {
	
	public AuditResponse generateResponse(AuditRequest request, String jwt);
	public List<AuditBenchmark> getBenchmarkData(String jwt);
}
