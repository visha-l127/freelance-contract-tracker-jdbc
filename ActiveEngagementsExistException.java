package com.freelance.util;

public class ActiveEngagementsExistException extends Exception{
	private String message;
	public ActiveEngagementsExistException(String message) {
		this.message=message;
	}
	public String tostring() {
		return message;
	}
}
  