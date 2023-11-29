package com.kanan.library.libraryspringbootapplication.dao.daoImpl;

import com.kanan.library.libraryspringbootapplication.dao.BookDao;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public BookDaoImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void saveBook(Book book) {
		mongoTemplate.save(book);
	}

	@Override
	public List<Book> findAll() {
		return mongoTemplate.findAll(Book.class);
	}

	@Override
	public Book findBookById(String bookId) {
		Query query = new Query(Criteria.where("bookId").is(bookId));
		return mongoTemplate.findOne(query, Book.class);
	}


	@Override
	public void updateBookById(Map<String, Object> updatedBook) {
		Query query = new Query(Criteria.where("bookId").is(updatedBook.get("bookId")));
		Update update = new Update();

		for (String key : updatedBook.keySet()) {

			if (updatedBook.get(key) == null) {
				continue;
			}

			try {

				Field field = Book.class.getDeclaredField(key);
				field.setAccessible(true);
				update.set(field.getName(), updatedBook.get(key));
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}
		mongoTemplate.updateFirst(query, update, Book.class);
	}

	@Override
	public void deleteBookById(String bookId) {
		Query query = new Query(Criteria.where("bookId").is(bookId));
		mongoTemplate.remove(query, Book.class);
	}

	@Override
	public boolean isBookNameTaken(String title) {
		Query query = new Query(Criteria.where("title").is(title));
		long count = mongoTemplate.count(query, Book.class);
		return count > 0;
	}

	@Override
	public List<Book> getBooksByAuthorName(String authorName) {
		Query query = new Query(Criteria.where("authorName").is(authorName));
		Author author = mongoTemplate.findOne(query, Author.class);

		if (author != null) {
			Query bookQuery = new Query(Criteria.where("authorId").is(author.getAuthorId()));
			return mongoTemplate.find(bookQuery, Book.class);
		}

		return Collections.emptyList();
	}

	@Override
	public List<Book> getPublishedBooksWithCondition(boolean includeUnPublished) {
		List<Book> bookList;
		Query query = new Query();
		if (includeUnPublished) {
			return mongoTemplate.findAll(Book.class);
		}

		Criteria criteria = Criteria.where("published").is(true);
		query.addCriteria(criteria);
		bookList = mongoTemplate.find(query, Book.class);

		return bookList;
	}


}
