package com.auditmanagement.severity.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagement.severity.api.model.AuthenticationResponse;

@FeignClient(name = "Authorization-audit", url = "${audit.auth}")
public interface AuthorizationFeign {

	@PostMapping("/validate")
	public ResponseEntity<AuthenticationResponse> validate(@RequestHeader("Authorization") String token);

}