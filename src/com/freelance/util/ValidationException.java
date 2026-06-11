package com.freelance.util;

public class ValidationException extends Exception{
	private String message;
	public ValidationException(String message){
		this.message=message;
	}
	public String toString() {
		return message;
	}
}
