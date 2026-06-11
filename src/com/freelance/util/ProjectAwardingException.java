package com.freelance.util;

public class ProjectAwardingException extends Exception{
	private String message;
	public ProjectAwardingException(String message) {
		this.message=message;
	}
	public String tostring() {
		return message;
	}

}
