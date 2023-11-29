package com.kanan.library.libraryspringbootapplication.service;

public interface AuthorCountService {
	void incrementAuthorsCount(String id);

	String getAuthorsCount(String id);
}
