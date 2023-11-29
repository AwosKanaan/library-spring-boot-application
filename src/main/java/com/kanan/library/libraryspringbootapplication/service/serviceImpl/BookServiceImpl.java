package com.kanan.library.libraryspringbootapplication.service.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanan.library.libraryspringbootapplication.dao.AuthorDao;
import com.kanan.library.libraryspringbootapplication.dao.BookCountDao;
import com.kanan.library.libraryspringbootapplication.dao.BookDao;
import com.kanan.library.libraryspringbootapplication.entity.Author;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.exception.AuthorCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.service.AuthorService;
import com.kanan.library.libraryspringbootapplication.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.kanan.library.libraryspringbootapplication.validation.Validate.validateBook;

@Service
public class BookServiceImpl implements BookService {

	private final BookDao bookDao;
	private final BookCountDao bookCountDao;

	private final AuthorService authorService;

	private final ObjectMapper objectMapper;

	@Autowired
	public BookServiceImpl(BookDao bookDao, BookCountDao bookCountDao, AuthorService authorService, ObjectMapper objectMapper) {
		this.bookDao = bookDao;
		this.bookCountDao = bookCountDao;
		this.authorService = authorService;
		this.objectMapper = objectMapper;
	}

	@Override
	public void saveBook(Book book) throws ConstraintViolationException, BookCollectionException {
		validateBook(book);

		try {
			authorService.findAuthorById(book.getAuthorId());
		} catch (AuthorCollectionException e) {
			throw new RuntimeException(e);
		}

		if (bookDao.isBookNameTaken(book.getTitle())) {
			throw new BookCollectionException(BookCollectionException.nameTakenException());
		}

		book.setId(null);
		book.setBookId(bookCountDao.incrementBooksCount("655dc557ad9bf6d94e7215b0"));

		bookDao.saveBook(book);
	}

	@Override
	public List<Book> findAll() {
		List<Book> books = bookDao.findAll();

		if(!books.isEmpty()) {
			return books;
		}
		return new ArrayList<>();
	}

	@Override
	public Book findBookById(String bookId) throws BookCollectionException{
		Book book =  bookDao.findBookById(bookId);

		if(book == null) {
			throw new BookCollectionException(BookCollectionException.notFoundException(bookId));
		}

		return book;
	}

	@Override
	public void updateBookById(Book book) throws BookCollectionException {
		validateBook(book);

		if (book.getBookId() == null) {
			throw new BookCollectionException("Book id cannot be null");
		}

		if (bookDao.findBookById(book.getBookId()) == null) {
			throw new BookCollectionException("Book with id " + book.getBookId() + " not found");
		}

		Map<String, Object> bookMap = objectMapper.convertValue(book, new TypeReference<>() {});
		bookDao.updateBookById(bookMap);
	}

	@Override
	public void deleteBookById(String bookId) throws BookCollectionException {
		Book book = bookDao.findBookById(bookId);

		if (book == null) {
			throw new BookCollectionException(BookCollectionException.notFoundException(bookId));
		}

		bookDao.deleteBookById(bookId);
	}

	@Override
	public List<Book> getBooksByAuthorName(String authorName) throws BookCollectionException {
		List<Book> bookList = bookDao.getBooksByAuthorName(authorName);

		if (bookList.isEmpty()) {
			throw new BookCollectionException(BookCollectionException.noAuthorsFound(authorName));
		}

		return bookList;
	}

	@Override
	public List<Book> getPublishedBooksWithCondition(boolean includeUnPublished) throws BookCollectionException {
		List<Book> bookList = bookDao.getPublishedBooksWithCondition(includeUnPublished);

		if (bookList.isEmpty()) {
			throw new BookCollectionException(BookCollectionException.emptyListException());
		}

		return bookList;
	}

}

