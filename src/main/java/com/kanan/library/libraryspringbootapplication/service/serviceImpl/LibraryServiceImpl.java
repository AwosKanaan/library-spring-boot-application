package com.kanan.library.libraryspringbootapplication.service.serviceImpl;

import com.kanan.library.libraryspringbootapplication.entity.Bill;
import com.kanan.library.libraryspringbootapplication.entity.Book;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;
import com.kanan.library.libraryspringbootapplication.service.BillService;
import com.kanan.library.libraryspringbootapplication.service.BookService;
import com.kanan.library.libraryspringbootapplication.service.LibraryService;
import com.kanan.library.libraryspringbootapplication.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

	private final PersonService personService;

	private final BookService bookService;

	private final BillService billService;

	@Autowired
	public LibraryServiceImpl(PersonService personService, BookService bookService, BillService billService) {
		this.personService = personService;
		this.bookService = bookService;
		this.billService = billService;
	}

	@Override
	public Person addToShoppingCart(String personId, String bookId) throws PersonCollectionException, BookCollectionException {
		Person person = personService.getPersonById(personId);
		System.err.println(person);

		bookService.findBookById(bookId);

		person.addToShoppingCart(bookId);
		personService.updatePersonShoppingCart(person, person.getBookIds());
		return person;
	}

	@Override
	public Person removeFromShoppingCart(String personId, String bookId) throws PersonCollectionException, BookCollectionException {
		Person person = personService.getPersonById(personId);

		bookService.findBookById(bookId);

		person.removeFromShoppingCart(bookId);
		personService.updatePersonShoppingCart(person, person.getBookIds());
		return person;
	}

	@Override
	public Bill checkOut(String personId) throws PersonCollectionException, BookCollectionException {
		Person person = personService.getPersonById(personId);

		List<String> bookIds = person.getBookIds();

		double totalAmount = calculateAmount(bookIds);

		Bill bill = new Bill();
		bill.setPersonId(personId);
		bill.setBookIds(bookIds);
		bill.setLocalDateTime(LocalDateTime.now());
		bill.setTotalAmount(totalAmount);

		billService.saveBill(bill);

		person.clearShoppingList();

		personService.updatePersonShoppingCart(person, person.getBookIds());

		return bill;
	}

	@Override
	public double calculateAmount(List<String> bookIds) throws BookCollectionException {
		double totalAmount = 0;

		for (String bookId : bookIds) {
			Book book = bookService.findBookById(bookId);
			totalAmount += book.getPrice();
		}

		return totalAmount;
	}


}
