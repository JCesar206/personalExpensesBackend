package com.julio.expensemanager.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleRuntimeException(RuntimeException ex) {
		
		log.error("Runtime exception occurred", ex);
		
		return Map.of(
			"timestamp", LocalDateTime.now(),
			"error", ex.getMessage()
		);
	}
}