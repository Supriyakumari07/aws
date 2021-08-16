package com.auditmanagement.severity.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.auditmanagement.severity.api.feign.AuthorizationFeign;
import com.auditmanagement.severity.api.model.AuthenticationResponse;
import com.auditmanagement.severity.api.service.AuthorizationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class AuthorizationServiceTests {

	@Mock
	AuthorizationFeign authClient;

	@InjectMocks
	AuthorizationServiceImpl authorizationServiceImpl;

	@Test
	void contextLoads() throws Exception {
		assertNotNull(authorizationServiceImpl);
	}

	@Test
	void testValidateJwt() {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse("null", "null", true);
		ResponseEntity<AuthenticationResponse> response = new ResponseEntity<AuthenticationResponse>(
				authenticationResponse, HttpStatus.OK);
		when(authClient.validate("jwt")).thenReturn(response);
		assertTrue(authorizationServiceImpl.validateJwt("jwt"));
	}

}
