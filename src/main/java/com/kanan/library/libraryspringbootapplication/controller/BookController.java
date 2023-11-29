package com.kanan.library.libraryspringbootapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class BookController {
	private final BookService bookService;
	private final ObjectMapper objectMapper;


	@Autowired
	public BookController(BookService bookService, ObjectMapper objectMapper) {
		this.bookService = bookService;
		this.objectMapper = objectMapper;
	}

	@PostMapping("/book")
	public ResponseEntity<?> saveBook(@RequestParam String book) {
		Book bookToSave;

		try {
			bookToSave = objectMapper.readValue(book, Book.class);
			bookService.saveBook(bookToSave);
			return new ResponseEntity<>(bookToSave, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/books")
	public ResponseEntity<?> getAllBooks() {
		List<Book> books = bookService.findAll();
		return new ResponseEntity<>(books, !books.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<?> getBookById(@PathVariable String bookId) {
		try {
			Book responseBook = bookService.findBookById(bookId);
			return new ResponseEntity<>(responseBook, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/book")
	public ResponseEntity<?> updateBookById(@RequestParam String book) {
		Book bookToUpdate;

		try {
			bookToUpdate = objectMapper.readValue(book, Book.class);
			bookService.updateBookById(bookToUpdate);
			return new ResponseEntity<>("updated book with id - " + bookToUpdate.getBookId(), HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> deleteBookById(@PathVariable String bookId) {
		try {
			bookService.deleteBookById(bookId);
			return new ResponseEntity<>("Book with id - " + bookId + " has been deleted successfully", HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("books/author/{authorName}")
	public ResponseEntity<?> getBooksByAuthorName(@PathVariable String authorName) {
		try {
			List<Book> bookList = bookService.getBooksByAuthorName(authorName);
			return new ResponseEntity<>(bookList, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/books/published")
	public ResponseEntity<?> getPublishedBooksWithCondition(@RequestParam boolean includeUnPublished) {
		try {
			System.out.println(includeUnPublished + "test");
			List<Book> bookList = bookService.getPublishedBooksWithCondition(includeUnPublished);
			return new ResponseEntity<>(bookList, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
