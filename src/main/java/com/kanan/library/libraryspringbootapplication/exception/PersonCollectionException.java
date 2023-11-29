package com.kanan.library.libraryspringbootapplication.exception;

public class PersonCollectionException extends RuntimeException {

	public PersonCollectionException(String message) {
		super(message);
	}

	public static String notFoundException(String id) {
		return "person with id - " + id + " does not exist";
	}
}
