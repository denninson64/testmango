package com.mango.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SloganNotFoundException extends RuntimeException {
	public SloganNotFoundException() {
		super("Slogan not found");
	}

	public SloganNotFoundException(String message) {
		super(message);
	}
}
