package com.kanan.library.libraryspringbootapplication.dao;

import com.kanan.library.libraryspringbootapplication.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface BookDao {
	 void saveBook(Book book);

	 List<Book> findAll();

	Book findBookById(String bookId);

	void updateBookById(Map<String, Object> bookMap);

	void deleteBookById(String bookId);

	boolean isBookNameTaken(String bookName);

	List<Book> getBooksByAuthorName(String authorName);

	List<Book> getPublishedBooksWithCondition(boolean includeUnPublished);
}
