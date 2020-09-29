package com.mindtree.omf.user.management.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler
	public String handleUserException(RuntimeException ex) {
		return ex.getMessage();
	}
}
