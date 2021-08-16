package com.auditmanagement.severity.api.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagement.severity.api.model.AuditBenchmark;

@FeignClient(value = "auditbenchmark", url = "${audit.benchmark}")
public interface AuditBenchmarkFeign {
	@GetMapping("/auditBenchmark")
	public List<AuditBenchmark> getAuditBenchmark(@RequestHeader("Authorization") String jwt);

}
