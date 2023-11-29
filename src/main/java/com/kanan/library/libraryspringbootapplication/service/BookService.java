package com.kanan.library.libraryspringbootapplication.service;

import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {

	void saveBook(Book book) throws BookCollectionException;

	 List<Book> findAll();

	 Book findBookById(String bookId) throws BookCollectionException;

	 void updateBookById(Book book) throws BookCollectionException;

	void deleteBookById(String bookId) throws BookCollectionException;

	List<Book> getBooksByAuthorName(String authorName) throws BookCollectionException;

	List<Book> getPublishedBooksWithCondition(boolean includeUnPublished) throws BookCollectionException;
}
