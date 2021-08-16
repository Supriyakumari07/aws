package com.auditmanagement.severity.api.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagement.severity.api.model.AuditQuestions;
import com.auditmanagement.severity.api.model.AuditType;

@FeignClient(url = "${audit.checklist}", name = "auditChecklist")
public interface AuditCheckListFeign {

	@PostMapping("/AuditCheckListQuestions")
	public List<AuditQuestions> auditCheckListQuestions(@RequestHeader("Authorization") String jwt,
			@RequestBody AuditType auditType);

}