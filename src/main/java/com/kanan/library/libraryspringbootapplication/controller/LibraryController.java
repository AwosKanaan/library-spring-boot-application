package com.kanan.library.libraryspringbootapplication.controller;


import com.kanan.library.libraryspringbootapplication.entity.Bill;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.service.LibraryService;
import com.kanan.library.libraryspringbootapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {


	private final LibraryService libraryService;

	@Autowired
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@PostMapping("library/{personId}/shopping-cart/add")
	public ResponseEntity<?> addToShoppingCart(@PathVariable String personId, @RequestParam String bookId) {
		try {
			Person updatedPerson = libraryService.addToShoppingCart(personId, bookId);
			return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("library/{personId}/shopping-cart/remove")
	public ResponseEntity<?> removeFromShoppingCart(@PathVariable String personId, @RequestParam String bookId) {

		try {
			Person updatedPerson = libraryService.removeFromShoppingCart(personId, bookId);
			return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("library/checkout/{personId}")
	public ResponseEntity<?> checkOut(@PathVariable String personId) {
		try {
			Bill bill = libraryService.checkOut(personId);
			return new ResponseEntity<>(bill, HttpStatus.OK);
		} catch (BookCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
