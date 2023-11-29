package com.kanan.library.libraryspringbootapplication.service.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.dao.PersonCountDao;
import com.kanan.library.libraryspringbootapplication.dao.PersonDao;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;
import com.kanan.library.libraryspringbootapplication.service.BookService;
import com.kanan.library.libraryspringbootapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.kanan.library.libraryspringbootapplication.validation.Validate.validatePerson;

@Service
public class PersonServiceImpl implements PersonService {

	private final PersonDao personDao;

	private final PersonCountDao personCountDao;

	private final BookService bookService;

	private final ObjectMapper objectMapper;

	@Autowired
	public PersonServiceImpl(PersonDao personDao, BookService bookService, ObjectMapper objectMapper, PersonCountDao personCountDao) {
		this.personDao = personDao;
		this.bookService = bookService;
		this.objectMapper = objectMapper;
		this.personCountDao = personCountDao;
	}

	@Override
	public void createPerson(Person person) {
		validatePerson(person);
		person.setId(null);
		person.setPersonId(personCountDao.incrementPersonsCount("65662522fa0099dfd8f31e52"));
		personDao.savePerson(person);
	}

	@Override
	public Person getPersonById(String personId) throws PersonCollectionException {
		Person person = personDao.findPersonById(personId);

		if (person == null) {
			throw new PersonCollectionException("Person with id " + personId + " not found");
		}

		return person;
	}

	@Override
	public List<Person> getAllPersons() {
		List<Person> personList = personDao.findAllPersons();

		if (!personList.isEmpty()) {
			return personList;
		}

		return Collections.emptyList();
	}

	@Override
	public void updatePerson(Person person) throws PersonCollectionException {
		validatePerson(person);
		String personId = person.getPersonId();

		if (personId == null) {
			throw new PersonCollectionException("Person id cannot be null");
		}

		if (personDao.findPersonById(personId) == null) {
			throw new PersonCollectionException("Person with id " + personId + " not found");
		}
		Map<String, Object> personMap = objectMapper.convertValue(person, new TypeReference<>() {});
		personDao.updatePersonById(personMap);
	}

	@Override
	public void deletePerson(String personId) throws PersonCollectionException {
		Person person = personDao.findPersonById(personId);

		if (person == null) {
			throw new PersonCollectionException("Person with id " + personId + " not found");
		}

		personDao.deletePersonById(personId);
	}

	@Override
	public void updatePersonShoppingCart(Person person, List<String> updatedShoppingCart) {
		personDao.updatePersonShoppingCart(person, updatedShoppingCart);
	}
}
