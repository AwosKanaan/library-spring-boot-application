package com.kanan.library.libraryspringbootapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;
import com.kanan.library.libraryspringbootapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
public class PersonController {
	private final PersonService personService;
	private final ObjectMapper objectMapper;

	@Autowired
	public PersonController(PersonService personService, ObjectMapper objectMapper) {
		this.personService = personService;
		this.objectMapper = objectMapper;
	}

	@PostMapping("/person")
	public ResponseEntity<?> savePerson(@RequestParam String person) {
		Person personToSave;

		try {
			personToSave = objectMapper.readValue(person, Person.class);
			personService.createPerson(personToSave);
			return new ResponseEntity<>(personToSave, HttpStatus.OK);
		} catch (PersonCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/persons")
	public ResponseEntity<?> getAllPersons() {
		List<Person> persons = personService.getAllPersons();
		return new ResponseEntity<>(persons, !persons.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@GetMapping("/persons/{personId}")
	public ResponseEntity<?> getPersonById(@PathVariable String personId) {
		try {
			Person responsePerson = personService.getPersonById(personId);
			return new ResponseEntity<>(responsePerson, HttpStatus.OK);
		} catch (PersonCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/person")
	public ResponseEntity<?> updatePersonById(@RequestParam String person) {
		Person personToUpdate;

		try {
			personToUpdate = objectMapper.readValue(person, Person.class);
			personService.updatePerson(personToUpdate);
			return new ResponseEntity<>("updated person with id - " + personToUpdate.getPersonId(), HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (JsonProcessingException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (PersonCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/persons/{personId}")
	public ResponseEntity<?> deletePersonById(@PathVariable String personId) {
		try {
			personService.deletePerson(personId);
			return new ResponseEntity<>("Person with id - " + personId + " has been deleted successfully", HttpStatus.OK);
		} catch (PersonCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
