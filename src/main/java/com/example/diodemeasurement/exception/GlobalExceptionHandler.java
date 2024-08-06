package com.example.diodemeasurement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

		@ExceptionHandler(UserAlreadyExists.class)
		public ResponseEntity<String> userAlreadyExistsException(UserAlreadyExists ex) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
		// You can add more exception handlers if needed
}
