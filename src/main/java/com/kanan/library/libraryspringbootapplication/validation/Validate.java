package com.kanan.library.libraryspringbootapplication.validation;

import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.entity.Person;

import javax.validation.ConstraintViolationException;

public class Validate {

	public static void validatePerson(Person person) {
		if (person == null) {
			throw new ConstraintViolationException("Person object cannot be null", null);
		}

		if (person.getName() == null || person.getName().trim().isEmpty()) {
			throw new ConstraintViolationException("Person name is required", null);
		}
	}

	public static void validateBook(Book book) {
		if (book == null) {
			throw new ConstraintViolationException("Book object cannot be null", null);
		}

		if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
			throw new ConstraintViolationException("Book title is required", null);
		}

		if (book.getAuthorId() == null || book.getAuthorId().trim().isEmpty()) {
			throw new ConstraintViolationException("Author id is required", null);
		}

		if (book.getISBN() == null || book.getISBN().trim().isEmpty()) {
			throw new ConstraintViolationException("ISBN is required", null);
		}

		if (book.getSerialNumber() <= 0) {
			throw new ConstraintViolationException("Serial number must be a positive integer", null);
		}

		if (book.getPrice() < 0) {
			throw new ConstraintViolationException("Price must be a non-negative value", null);
		}

		if (book.getPublished() == null) {
			throw new ConstraintViolationException("You have to set the status of the book", null);
		}

		if (book.getQuantity() <= 0) {
			throw new ConstraintViolationException("Book quantity must be greater than 0", null);
		}
	}

	public static void validateAuthor(Author author) {
		if (author == null) {
			throw new ConstraintViolationException("Author object cannot be null", null);
		}

		if (author.getAuthorName() == null || author.getAuthorName().trim().isEmpty()) {
			throw new ConstraintViolationException("Author name is required", null);
		}
	}

}
