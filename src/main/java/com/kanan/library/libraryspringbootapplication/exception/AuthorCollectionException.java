package com.kanan.library.libraryspringbootapplication.exception;

public class AuthorCollectionException extends Exception {
	public AuthorCollectionException(String message) {
		super(message);
	}

	public static String notFoundException(String id) {
		return "Author with id - " + id + " is not found.";
	}

	public static String authorAlreadyExistsException() {
		return "Author with given name already exists!";
	}

	public static String nameTakenException() {
		return "name is already taken";
	}
}
