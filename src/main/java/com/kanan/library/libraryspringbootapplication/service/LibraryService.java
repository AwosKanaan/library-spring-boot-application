package com.kanan.library.libraryspringbootapplication.service;

import com.kanan.library.libraryspringbootapplication.entity.Bill;
import com.kanan.library.libraryspringbootapplication.entity.Person;
import com.kanan.library.libraryspringbootapplication.exception.BookCollectionException;
import com.kanan.library.libraryspringbootapplication.exception.PersonCollectionException;

import java.util.List;

public interface LibraryService {

	Person addToShoppingCart(String personId, String bookId) throws PersonCollectionException, BookCollectionException;

	Person removeFromShoppingCart(String personId, String bookId) throws PersonCollectionException, BookCollectionException;

	Bill checkOut(String personId) throws PersonCollectionException, BookCollectionException;

	double calculateAmount(List<String> bookIds) throws BookCollectionException;
}
