package com.kanan.library.libraryspringbootapplication.exception;

public class BookCollectionException extends Exception {

	public BookCollectionException(String message) {
		super(message);
	}

	public static String notFoundException(String id) {
		return "Book with id - " + id + " not found.";
	}

	public static String bookAlreadyExistsException() {
		return "Book with given name already exists!";
	}

	public static String nameTakenException() {
		return "title is already taken";
	}

	public static String noAuthorsFound(String authorName) {
		return "No books associated with - " + authorName + " found.";
	}

	public static String emptyListException() {
		return "No books found";
	}
}
