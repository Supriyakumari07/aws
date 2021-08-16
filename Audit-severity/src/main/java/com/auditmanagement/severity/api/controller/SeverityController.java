package com.auditmanagement.severity.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auditmanagement.severity.api.exception.BenchmarkException;
import com.auditmanagement.severity.api.exception.FeignProxyException;
import com.auditmanagement.severity.api.model.AuditRequest;
import com.auditmanagement.severity.api.model.AuditResponse;
import com.auditmanagement.severity.api.service.AuthorizationService;
import com.auditmanagement.severity.api.service.SeverityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/severity")
@CrossOrigin(origins = "*")
public class SeverityController {

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private SeverityService severityService;

	@PostMapping("/project-execution-status")
	public ResponseEntity<?> auditSeverity(@RequestHeader("Authorization") String jwt,
			@RequestBody AuditRequest auditRequest) throws BenchmarkException, FeignProxyException {

		var auditResponse = new AuditResponse();

		// This condition is to check for authorized user
		if (jwt.length() > 0 && authorizationService.validateJwt(jwt)) {

			auditResponse = severityService.generateResponse(auditRequest, jwt);

			if (auditResponse == null) {
				log.error("Benchmark List is Empty");
				throw new BenchmarkException();

			} else if (auditResponse.getAuditId() == -1) {
				log.error("Benchmark MS is not working");
				throw new FeignProxyException();
			}

			log.info("AuditResponse successfully created!!");

			return new ResponseEntity<>(auditResponse, HttpStatus.OK);
		}
		return new ResponseEntity<>(auditResponse, HttpStatus.FORBIDDEN);

	}
}
