package com.kanan.library.libraryspringbootapplication.service;

import org.springframework.stereotype.Service;

public interface BookCountService {
	void incrementBooksCount(String id);

	int getBooksCount(String id);
}
