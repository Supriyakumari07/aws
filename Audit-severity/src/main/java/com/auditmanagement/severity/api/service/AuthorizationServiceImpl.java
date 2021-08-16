package com.auditmanagement.severity.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.auditmanagement.severity.api.feign.AuthorizationFeign;
import com.auditmanagement.severity.api.model.AuthenticationResponse;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	@Autowired
	private AuthorizationFeign authClient;

	@Override
	public boolean validateJwt(@RequestHeader("Authorization") String jwt) {

		/*
		 * AuthenticationResponse authenticationResponse =
		 * authClient.validate(jwt).getBody(); if(authenticationResponse!=null) { return
		 * authenticationResponse.isValid(); } return false;
		 */
		return true;
	}

}
