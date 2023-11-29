package com.kanan.library.libraryspringbootapplication.service;

import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;
import java.util.List;

public interface PersonService {

	void createPerson(Person person);

	Person getPersonById(String personId) throws PersonCollectionException;

	List<Person> getAllPersons();

	void updatePerson(Person person) throws PersonCollectionException;

	void deletePerson(String personId) throws PersonCollectionException;
	void updatePersonShoppingCart(Person person, List<String> updatedShoppingCart);
}
