package com.kanan.library.libraryspringbootapplication.dao;

import com.kanan.library.libraryspringbootapplication.entity.Person;
import java.util.List;
import java.util.Map;

public interface PersonDao {

	void savePerson(Person person);

	List<Person> findAllPersons();

	Person findPersonById(String personId);

	void updatePersonById(Map<String, Object> personMap);

	void deletePersonById(String personId);

	void updatePersonShoppingCart(Person person, List<String> updatedShoppingCart);

	void addToShoppingCart(Person person, String bookId);

	void deleteFromShoppingCart(Person person, String bookId);
}
