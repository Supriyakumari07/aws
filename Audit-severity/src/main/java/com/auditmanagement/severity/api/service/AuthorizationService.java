package com.auditmanagement.severity.api.service;

import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthorizationService {

	public boolean validateJwt(@RequestHeader("Authorization") String jwt);
}
