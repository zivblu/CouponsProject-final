package com.example.demo.exceptions;

public class WrongLoginInputException extends Exception {

	public WrongLoginInputException(String message) {
		super(message);
	}

}