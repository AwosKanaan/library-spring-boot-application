package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.PersonDao;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Repository
public class PersonDaoImpl implements PersonDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public PersonDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void savePerson(Person person) {
		mongoTemplate.save(person);
	}

	@Override
	public List<Person> findAllPersons() {
		return mongoTemplate.findAll(Person.class);
	}

	@Override
	public Person findPersonById(String personId) {
		Query query = new Query(Criteria.where("personId").is(personId));
		return mongoTemplate.findOne(query, Person.class);
	}

	@Override
	public void updatePersonById(Map<String, Object> personMap) {
		Query query = new Query(Criteria.where("personId").is(personMap.get("personId")));
		Update update = new Update();

		for (String key: personMap.keySet()) {

			if (personMap.get(key) == null) {
				continue;
			}

			try {
				Field field = Person.class.getDeclaredField(key);
				field.setAccessible(true);
				update.set(field.getName(), personMap.get(key));
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}

		mongoTemplate.updateFirst(query, update, Person.class);
	}

	@Override
	public void deletePersonById(String personId) {
		Query query = new Query(Criteria.where("personId").is(personId));
		mongoTemplate.remove(query, Person.class);
	}

	@Override
	public void updatePersonShoppingCart(Person person, List<String> updatedShoppingCart) {
		Query query = new Query(Criteria.where("personId").is(person.getPersonId()));
		Update update = new Update();

		update.set("bookIds", updatedShoppingCart);

		mongoTemplate.updateFirst(query, update, Person.class);
	}

	@Override
	public void addToShoppingCart(Person person, String bookId) {
		person.addToShoppingCart(bookId);
	}

	@Override
	public void deleteFromShoppingCart(Person person, String bookId) {

	}
}
