package com.mango.customer.exception;

public class SloganLimitExceededException extends Exception {
	public SloganLimitExceededException() {
		super("User already has the maximum number of slogans");
	}
}
