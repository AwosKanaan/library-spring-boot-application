package com.kanan.library.libraryspringbootapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.exception.AuthorCollectionException;
import com.kanan.library.libraryspringbootapplication.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
public class AuthorController {


	private final AuthorService authorService;
	private final ObjectMapper objectMapper;

	@Autowired
	public AuthorController(AuthorService authorService, ObjectMapper objectMapper) {
		this.authorService = authorService;
		this.objectMapper = objectMapper;
	}


	@PostMapping("/author")
	public ResponseEntity<?> saveAuthor(
			@RequestParam String author) {
		Author authorToSave;

		try {
			authorToSave = objectMapper.readValue(author, Author.class);
			authorService.saveAuthor(authorToSave);
			return new ResponseEntity<>(authorToSave, HttpStatus.OK);
		} catch (AuthorCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/authors")
	public ResponseEntity<?> getAllAuthors() {
		List<Author> authors = authorService.findAll();
		return new ResponseEntity<>(authors, !authors.isEmpty()? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/authors/{authorId}")
	public ResponseEntity<?> getAuthorById(@PathVariable String authorId) {
		try {
			Author authorResponse = authorService.findAuthorById(authorId);
			return new ResponseEntity<>(authorResponse, HttpStatus.OK) ;
		} catch (AuthorCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/author")
	public ResponseEntity<?> updateAuthorById(@RequestParam String author) {
		Author authorToUpdate;

		try {
			authorToUpdate = objectMapper.readValue(author, Author.class);
			authorService.updateAuthorById(authorToUpdate);
			return new ResponseEntity<>("updated author with id - " + authorToUpdate.getAuthorId(), HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthorCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/authors/{authorId}")
	public ResponseEntity<?> deleteAuthorById(@PathVariable String authorId) {
		try {
			authorService.deleteAuthorById(authorId);
			return new ResponseEntity<>("Author with id - " + authorId + " has been deleted successfully", HttpStatus.OK);
		} catch (AuthorCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
