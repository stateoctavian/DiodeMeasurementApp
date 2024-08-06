package com.example.diodemeasurement.exception;

public class UserAlreadyExists extends RuntimeException{

		public UserAlreadyExists(String message, Exception e) {
				super(message, e);
		}
		public UserAlreadyExists(String message) {
				super(message);
		}
}
