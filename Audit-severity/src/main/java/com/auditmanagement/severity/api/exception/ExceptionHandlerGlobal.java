package com.auditmanagement.severity.api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auditmanagement.severity.api.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerGlobal {

	@ExceptionHandler(FeignProxyException.class)
	public ResponseEntity<ErrorResponse> handleFeignProxyException(FeignProxyException ex) {
		log.info("start");
		log.debug("FeignProxyException", ex);
		var response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage("Not able to connect with Benchmark");
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setReason("Internal Connection Failure");
		log.info("end");
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(BenchmarkException.class)
	public ResponseEntity<ErrorResponse> handleBenchmarkException(BenchmarkException ex) {
		log.info("start");
		log.debug("BenchMarkException", ex);
		var response = new ErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage("Benchmark List is Empty");
		response.setStatus(HttpStatus.NO_CONTENT);
		response.setReason("Empty Benchmark List");
		log.info("end");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
