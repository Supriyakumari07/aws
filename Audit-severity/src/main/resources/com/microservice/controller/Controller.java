package com.microservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.Benchmark;
import com.microservice.service.BenchmarkService;
import com.microservice.service.AuthorizationToken;

@RestController
@RequestMapping("/benchmark")
@CrossOrigin(origins = "*")
public class Controller {
	
	@Autowired
	private AuthorizationToken authorizationTokens;
	
	@Autowired
	private BenchmarkService benchmarkService;
	
	Logger logger = LoggerFactory.getLogger("Benchmark-Controller-Logger");
	
	@GetMapping("/AuditBenchmark")
	public List<Benchmark> getAuditBenchmark(@RequestHeader("Authorization") String token){
		List<Benchmark> benchmarks = new ArrayList<>();
		
		//Checking valid token or not
		logger.info("From header Token ::" +token);
		
		if(token.length()>0 && authorizationTokens.validateToken(token)) {
			benchmarks = benchmarkService.getAuditBenchmarkList();
		}
		else {
			logger.error("Failed to validate Token ::" + token);
		}
		
		
		return benchmarks;
	}
	
	// Endpoint to check if the microservice is live
		@GetMapping("/health-check")
		public String healthCheck() {
			return "Audit Benchmark Microservice is Active";
		}
	

}
