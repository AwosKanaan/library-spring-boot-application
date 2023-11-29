package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.AuthorDao;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoImpl implements AuthorDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public AuthorDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void saveAuthor(Author author) {
		mongoTemplate.save(author);
	}

	public List<Author> findAll() {
		return mongoTemplate.findAll(Author.class);
	}

	public Author findAuthorById(String authorId) {
		Query query = new Query(Criteria.where("authorId").is(authorId));
		return mongoTemplate.findOne(query, Author.class);
	}

	@Override
	public void updateAuthorById(Map<String, Object> updatedAuthor) {
		Query query = new Query(Criteria.where("authorId").is(updatedAuthor.get("authorId")));
		Update update = new Update();

		for(String key: updatedAuthor.keySet()) {

			if (updatedAuthor.get(key) == null) {
				continue;
			}

			try {
				Field field = Author.class.getDeclaredField(key);
				field.setAccessible(true);
				update.set(field.getName(), updatedAuthor.get(key));
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}
		mongoTemplate.updateFirst(query, update, Author.class);
	}

	public void deleteAuthorById(String authorId) {
		Query query = new Query(Criteria.where("authorId").is(authorId));
		mongoTemplate.remove(query, Author.class);
	}

	@Override
	public boolean isAuthorNameTaken(String authorName) {
		Query query = new Query(Criteria.where("authorName").is(authorName));
		long count = mongoTemplate.count(query, Author.class);
		return count > 0;
	}
}
